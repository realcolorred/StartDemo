package com.example.common.constant.table;

/**
 * Created by new on 2020/8/17.
 */
public interface CommConst {

    enum STATUS {
        DELETED(0, "DELETED", "已删除"),
        ACTIVE(1, "ACTIVE", "有效");

        private Integer value;
        private String  name;
        private String  cnName;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public String getCnName() {
            return cnName;
        }

        STATUS(Integer value, String name, String cnName) {
            this.value = value;
            this.name = name;
            this.cnName = cnName;
        }

        public static String getNameByValue(Integer value) {
            for (STATUS status : STATUS.values()) {
                if (status.value.equals(value)) {
                    return status.name();
                }
            }
            return "";
        }
    }
}
