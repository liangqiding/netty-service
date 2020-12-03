package com.netty.service.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-11-25 14:49
 * @version v1.0.0
 */
@NoArgsConstructor
@Data
public class Device {

    /**
     * _id : 200730@LT-102M@0005_SD103@0001
     * productkey : SD103
     * device_info : {"enable":"true","name":"","node_type":"终端设备","reg_datetime":"2020-08-10 17:09:43","activationd_atetime":"","last_online_datetime":"","swveriosn":"","hwversion":""}
     * parent_devid : 200730@LT-102M@0005
     * device_topiclist : [{"topic":"i2dsp/Emg/${Lv}/SD-103/200730@LT-102M@0005_SD103@0001/thing/event/property/post","operst":"发布","desc":"设备属性上报","operat":0},{"topic":"i2dsp/Emg/${Lv}/SD-103/200730@LT-102M@0005_SD103@0001/thing/event/${function_tag}/post","operst":"发布","desc":"设备事件上报","operat":0},{"topic":"i2dsp/Emg/SD-103/200730@LT-102M@0005/200730@LT-102M@0005_SD103@0001/thing/service/property/get/${userid}","operst":"订阅","desc":"设备属性获取","operat":0},{"topic":"i2dsp/Emg/${Lv}/SD-103/200730@LT-102M@0005_SD103@0001/thing/service/property/reply/get/${userid}","operst":"发布","desc":"设备属性获取回复","operat":0},{"topic":"i2dsp/Emg/SD-103/200730@LT-102M@0005/200730@LT-102M@0005_SD103@0001/thing/service/property/set/${userid}","operst":"订阅","desc":"设备属性设置","operat":0},{"topic":"i2dsp/Emg/${Lv}/SD-103/200730@LT-102M@0005_SD103@0001/thing/service/property/reply/set/${userid}","operst":"发布","desc":"设备属性设置回复","operat":0},{"topic":"i2dsp/Emg/SD-103/200730@LT-102M@0005/200730@LT-102M@0005_SD103@0001/thing/service/function/${userid}}","operst":"订阅","desc":"设备事件调用","operat":0},{"topic":"i2dsp/Emg/${Lv}/SD-103/200730@LT-102M@0005_SD103@0001/thing/service/reply/function/${userid}","operst":"发布","desc":"设备事件调用回复","operat":0},{"topic":"i2dsp/ota/emg/device/upgrade/SD-103","operst":"订阅","desc":"服务通知机种升级","operat":0},{"topic":"i2dsp/ota/emg/device/request/SD-103/200730@LT-102M@0005_SD103@0001","operst":"发布","desc":"设备升级查询","operat":0},{"topic":"i2dsp/ota/emg/device/upgrade/SD-103/200730@LT-102M@0005_SD103@0001","operst":"订阅","desc":"设备升级查询回复","operat":0},{"topic":"i2dsp/ota/emg/device/progress/SD-103/200730@LT-102M@0005_SD103@0001","operst":"发布","desc":"设备升级进度回复","operat":0}]
     * device_state : [{"function_tag":"SD103Emg","value":1},{"function_tag":"OnlineState","value":1},{"function_tag":"SD103Battery","value":100},{"function_tag":"SD103SmokeLevel","value":0},{"function_tag":"SD103BatteryProperty","value":0},{"function_tag":"SD103SmokeEmgThresholdValue","value":0}]
     * _class : com.i2dsp.i2dspcloudv2.Model.Device
     */

    private String _id;
    private String productkey;
    private DeviceInfoBean device_info;
    private String parent_devid;
    private String _class;
    private List<DeviceTopiclistBean> device_topiclist;
    private List<DeviceStateBean> device_state;

    @NoArgsConstructor
    @Data
    public static class DeviceInfoBean {
        /**
         * enable : true
         * name :
         * node_type : 终端设备
         * reg_datetime : 2020-08-10 17:09:43
         * activationd_atetime :
         * last_online_datetime :
         * swveriosn :
         * hwversion :
         */

        private String enable;
        private String name;
        private String node_type;
        private String reg_datetime;
        private String activationd_atetime;
        private String last_online_datetime;
        private String swveriosn;
        private String hwversion;
    }

    @NoArgsConstructor
    @Data
    public static class DeviceTopiclistBean  {
        /**
         * topic : i2dsp/Emg/${Lv}/SD-103/200730@LT-102M@0005_SD103@0001/thing/event/property/post
         * operst : 发布
         * desc : 设备属性上报
         * operat : 0
         */

        private String topic;
        private String operst;
        private String desc;
        private int operat;
    }

    @NoArgsConstructor
    @Data
    public static class DeviceStateBean  {
        /**
         * function_tag : SD103Emg
         * value : 1
         */

        private String function_tag;
        private int value;
    }
}
