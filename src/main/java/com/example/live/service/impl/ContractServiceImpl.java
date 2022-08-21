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
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
            if (u.getLevel()==1) {
                // 超管
                query.setAdmin11(true);
            } else {
                // 管理员
                opeUserIds = commonService.opeUserIds(u.getId());
            }
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
    public BaseResult<?> contractView(String id, HttpServletResponse response) {
        //获取合同下载url
        String pdfUrl = cloudSignUtil.signDown(id);
        if (StringUtils.isBlank(pdfUrl)) {
            return new BaseResult<>();
        }

        String filePath = Constant.TEMPORARY + "/" + id + ".pdf";
        try {
            URL url = new URL(pdfUrl);
            File file = new File(filePath);
            FileUtils.copyURLToFile(url, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取文件
        byte[] read = null;
        try {
            read = FileUtils.readFileToByteArray(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将文件内容 byte[]，通过 response 返回给客户端进行下载
        if (read != null && read.length > 0) {
            try {
                response.setContentType("application/pdf");
                response.setHeader("Content-Length", String.valueOf(read.length));
                response.getOutputStream().write(read);
                response.flushBuffer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    FileUtils.forceDelete(new File(filePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BaseResult<>();
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
