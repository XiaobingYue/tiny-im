package com.yxb.tinylove.netty.protocol.resp;

import com.yxb.tinylove.common.bean.Session;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author yxb
 * @since 2021/12/30
 */

@Data
@Builder
public class OnlineListResp {

    private Integer type;

    private List<Session> userList;
}
