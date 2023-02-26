package com.ilovesshan.couponunion.entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/26
 * @description:
 */
public class Ticket {

    private boolean success;
    private int code;
    private String message;
    private OuterData data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OuterData getData() {
        return data;
    }

    public void setData(OuterData outerData) {
        this.data = outerData;
    }

    public static class OuterData {
        private TbkTpwdCreateResponse tbk_tpwd_create_response;

        public TbkTpwdCreateResponse getTbk_tpwd_create_response() {
            return tbk_tpwd_create_response;
        }

        public void setTbk_tpwd_create_response(TbkTpwdCreateResponse tbk_tpwd_create_response) {
            this.tbk_tpwd_create_response = tbk_tpwd_create_response;
        }

        public static class TbkTpwdCreateResponse {
            private OuterData.TbkTpwdCreateResponse.Data data;
            private String request_id;

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            public static class Data {
                private String model;

                public String getModel() {
                    return model;
                }

                public void setModel(String model) {
                    this.model = model;
                }
            }
        }
    }
}
