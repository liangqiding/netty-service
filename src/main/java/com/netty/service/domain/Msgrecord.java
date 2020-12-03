package com.netty.service.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;


/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-11-25 10:58
 * @version v1.0.0
 */
@NoArgsConstructor
@Data
public class Msgrecord implements Serializable {

    /**
     * device_id : 200730@LT-102M@0005_SD103@0003
     * datetime : 2020-08-17 15:08:43
     * function_tag : SD103Emg
     * value : 1
     * address : [{"province":"广东省","city":"东莞市","town":"横沥镇","addcode":0,"postcode":0,"lat":113.950391,"lng":23.027112,"detailaddress":{"gardencode":"A","gardenname":"石涌村","buildingcode":"A","buildingname":"石涌村委会大楼","floorcode":2,"floorname":"二楼","place":"三楼走道","xpos":310,"ypos":280,"mapid":{"projectid":"B_ShiChong_200714001","fileid":"A1B1F2.png"},"extend":{"unitcode":"","householdcode":"","rooomcode":""}}}]
     */

    private String device_id;
    private String datetime;
    private String function_tag;
    private int value;
    private List<AddressBean> address;

    @NoArgsConstructor
    @Data
    public static class AddressBean {
        /**
         * province : 广东省
         * city : 东莞市
         * town : 横沥镇
         * addcode : 0
         * postcode : 0
         * lat : 113.950391
         * lng : 23.027112
         * detailaddress : {"gardencode":"A","gardenname":"石涌村","buildingcode":"A","buildingname":"石涌村委会大楼","floorcode":2,"floorname":"二楼","place":"三楼走道","xpos":310,"ypos":280,"mapid":{"projectid":"B_ShiChong_200714001","fileid":"A1B1F2.png"},"extend":{"unitcode":"","householdcode":"","rooomcode":""}}
         */

        private String province;
        private String city;
        private String town;
        private int addcode;
        private int postcode;
        private double lat;
        private double lng;
        private DetailaddressBean detailaddress;

        @NoArgsConstructor
        @Data
        public static class DetailaddressBean {
            /**
             * gardencode : A
             * gardenname : 石涌村
             * buildingcode : A
             * buildingname : 石涌村委会大楼
             * floorcode : 2
             * floorname : 二楼
             * place : 三楼走道
             * xpos : 310
             * ypos : 280
             * mapid : {"projectid":"B_ShiChong_200714001","fileid":"A1B1F2.png"}
             * extend : {"unitcode":"","householdcode":"","rooomcode":""}
             */

            private String gardencode;
            private String gardenname;
            private String buildingcode;
            private String buildingname;
            private int floorcode;
            private String floorname;
            private String place;
            private int xpos;
            private int ypos;
            private MapidBean mapid;
            private ExtendBean extend;

            @NoArgsConstructor
            @Data
            public static class MapidBean {
                /**
                 * projectid : B_ShiChong_200714001
                 * fileid : A1B1F2.png
                 */

                private String projectid;
                private String fileid;
            }

            @NoArgsConstructor
            @Data
            public static class ExtendBean {
                /**
                 * unitcode :
                 * householdcode :
                 * rooomcode :
                 */

                private String unitcode;
                private String householdcode;
                private String rooomcode;
            }
        }
    }
}
