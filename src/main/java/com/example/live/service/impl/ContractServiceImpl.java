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
import com.example.live.service.ContractService;
import com.example.live.util.CloudSignUtil;
import com.example.live.util.GeneralUtil;
import com.example.live.util.UserUtil;
import com.example.live.vo.ContractVO;
import com.example.live.vo.MerchantVO;
import com.example.live.vo.UserVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public BaseResult<?> contractList(ContractQuery query) {
        int count = contractMapper.contractCount(query);
        if (count==0) {
            return new BaseResult<>();
        }
        query.setPage(GeneralUtil.indexPage(query.getPage()));
        List<Contract> data = contractMapper.contractList(query);
        List<Integer> mids = Lists.newArrayList();
        List<Integer> uids = Lists.newArrayList();
        data.forEach(c -> {
            mids.add(c.getMerchantId());
            uids.add(c.getOpeUser());
        });
        List<User> userList = userMapper.userList2(uids);
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(), (k1, k2) -> k2));
        List<Merchant> merchantList = merchantMapper.merchantList(mids);
        Map<Integer, Merchant> merchantMap = merchantList.stream().collect(Collectors.toMap(Merchant::getId, Function.identity(), (k1, k2) -> k2));
        List<ContractVO> voList = Lists.newLinkedList();
        data.forEach(c ->{
            Merchant merchant = merchantMap.get(c.getMerchantId());
            User user = userMap.get(c.getOpeUser());
            ContractVO vo = new ContractVO();
            vo.setId(c.getId());
            vo.setBuyType(Constant.buyTypeMap.get(c.getBuyType()));
            vo.setSignType(Constant.signTypeMap.get(c.getSignType()));
            vo.setCt(c.getCt());
            vo.setUt(c.getUt());
            vo.setTax(c.getTax());
            vo.setDocumentId(c.getDocumentId());
            vo.setDocumentName(c.getDocumentName());
            vo.setMobile(c.getMobile());
            vo.setCompany(c.getSubject());
            vo.setOwner(c.getPerson());
            vo.setMerchantId(c.getMerchantId());
            if (merchant!=null) {
                vo.setShop(merchant.getShop());
            }
            if (user!=null) {
                vo.setOpeUser(user.getId()+"/"+user.getRemark()+"/"+user.getMobile());
            }
            voList.add(vo);
        });
        return new BaseResult<>(count, voList);
    }

    @Override
    public BaseResult<?> contractInfo(String id) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String url = cloudSignUtil.signDown(id);
        return new BaseResult<>(url);
    }

    @Override
    public BaseResult<?> contractDown(String id) {
        MerchantVO mvo = UserUtil.getMerchant();
        if (mvo == null) {
            return new BaseResult<>(BaseEnum.No_Login);
        }
        String url = cloudSignUtil.signDown(id);
        return new BaseResult<>(url);
    }
}
