package com.example.live.service.impl;

import com.example.live.common.BaseEnum;
import com.example.live.common.BaseResult;
import com.example.live.common.Constant;
import com.example.live.controller.query.ContractQuery;
import com.example.live.entity.Contract;
import com.example.live.entity.Merchant;
import com.example.live.entity.User;
import com.example.live.mapper.ContractMapper;
import com.example.live.mapper.MerchantMapper;
import com.example.live.mapper.UserMapper;
import com.example.live.service.CommonService;
import com.example.live.service.ContractService;
import com.example.live.single.AsyncService;
import com.example.live.util.CloudSignUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.ContractVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:48
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private MerchantMapper merchantMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CloudSignUtil cloudSignUtil;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AsyncService asyncService;


    @Override
    public BaseResult<?> contractList(ContractQuery query) {
        UserVO u = UserUtil.getUser();
        if (u==null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        List<Integer> opeUserIds = Lists.newArrayList();
        if (u.getLevel()!=3) {
            // 不是业务员级别
            opeUserIds = commonService.opeUserIds(u.getId());
        }
        opeUserIds.add(u.getId());
        query.setOpeUserIds(opeUserIds);
        int count = contractMapper.contractCount(query);
        if (count==0) {
            return new BaseResult<>();
        }
        query.setPage(GeneralUtil.indexPage(query.getPage()));
        List<Contract> data = contractMapper.contractList(query);
        List<Integer> mids = Lists.newArrayList();
        List<Integer> uids = Lists.newArrayList();
        List<String> flowIds = Lists.newArrayList();
        data.forEach(c -> {
            mids.add(c.getMerchantId());
            uids.add(c.getOpeUser());
            if (c.getSignStatus()==0) {
                flowIds.add(c.getFlowId());
            }
        });
        List<User> userList = userMapper.userList2(uids);
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(), (k1, k2) -> k2));
        List<Merchant> merchantList = merchantMapper.merchantList(mids);
        Map<Integer, Merchant> merchantMap = merchantList.stream().collect(Collectors.toMap(Merchant::getId, Function.identity(), (k1, k2) -> k2));
        Map<String, String> signMap = cloudSignUtil.signStatusMap(flowIds);
        List<ContractVO> voList = Lists.newLinkedList();

        // 回写status=1
        List<Integer> status1List = Lists.newArrayList();
        data.forEach(c ->{
            ContractVO vo = new ContractVO();
            vo.setId(c.getId());
            vo.setCt(c.getCt());
            vo.setUt(c.getUt());
            vo.setTax(c.getTax());
            vo.setOwner(c.getPerson());
            vo.setMobile(c.getMobile());
            vo.setCompany(c.getSubject());
            vo.setMerchantId(c.getMerchantId());
            vo.setDocumentId(c.getDocumentId());
            vo.setDocumentName(c.getDocumentName());
            vo.setBuyType(Constant.buyTypeMap.get(c.getBuyType()));
            vo.setSignType(Constant.signTypeMap.get(c.getSignType()));
            vo.setSignStatus(Constant.signStatusMap.get(c.getSignStatus()));
            Merchant merchant = merchantMap.get(c.getMerchantId());
            if (merchant!=null) {
                vo.setShop(merchant.getShop());
            }
            User user = userMap.get(c.getOpeUser());
            if (user!=null) {
                vo.setOpeUser(GeneralUtil.opeUserHandler(user.getId(), user.getRemark(), user.getMobile()));
            }
            if (signMap!=null) {
                String val = signMap.get(c.getFlowId());
                if ("已签".equals(val)) {
                    vo.setSignStatus(val);
                    status1List.add(c.getId());
                }
            }
            voList.add(vo);
        });
        // async
        asyncService.asyncSignStatusHandler(status1List);
        return new BaseResult<>(count, voList);
    }

    @Override
    public BaseResult<?> contractInfo(String id) {
        UserVO uvo = UserUtil.getUser();
        if (uvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String url = cloudSignUtil.signDown(id);
        return new BaseResult<>(url);
    }

    @Override
    public BaseResult<?> contractDown(String id) {
        UserVO uvo = UserUtil.getUser();
        if (uvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String url = cloudSignUtil.signDown(id);
        return new BaseResult<>(url);
    }
}
