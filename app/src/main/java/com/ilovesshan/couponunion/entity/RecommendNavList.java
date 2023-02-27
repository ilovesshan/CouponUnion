package com.ilovesshan.couponunion.entity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */

public class RecommendNavList {

    private boolean success;
    private int code;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private TbkUatmFavoritesItemGetResponse tbk_uatm_favorites_item_get_response;

        public TbkUatmFavoritesItemGetResponse getTbk_uatm_favorites_item_get_response() {
            return tbk_uatm_favorites_item_get_response;
        }

        public void setTbk_uatm_favorites_item_get_response(TbkUatmFavoritesItemGetResponse tbk_uatm_favorites_item_get_response) {
            this.tbk_uatm_favorites_item_get_response = tbk_uatm_favorites_item_get_response;
        }

        public static class TbkUatmFavoritesItemGetResponse {
            private Results results;
            private int total_results;
            private String request_id;

            public Results getResults() {
                return results;
            }

            public void setResults(Results results) {
                this.results = results;
            }

            public int getTotal_results() {
                return total_results;
            }

            public void setTotal_results(int total_results) {
                this.total_results = total_results;
            }

            public String getRequest_id() {
                return request_id;
            }

            public void setRequest_id(String request_id) {
                this.request_id = request_id;
            }

            public static class Results {
                private int favoriteId;
                private List<UatmTbkItem> uatm_tbk_item;

                public int getFavoriteId() {
                    return favoriteId;
                }

                public void setFavoriteId(int favoriteId) {
                    this.favoriteId = favoriteId;
                }

                public List<UatmTbkItem> getUatm_tbk_item() {
                    return uatm_tbk_item;
                }

                public void setUatm_tbk_item(List<UatmTbkItem> uatm_tbk_item) {
                    this.uatm_tbk_item = uatm_tbk_item;
                }

                public static class UatmTbkItem {
                    private String click_url;
                    private String coupon_click_url;
                    private String coupon_end_time;
                    private String coupon_info;
                    private int coupon_remain_count;
                    private String coupon_start_time;
                    private int coupon_total_count;
                    private String event_end_time;
                    private String event_start_time;
                    private String item_url;
                    private long num_iid;
                    private String pict_url;
                    private String reserve_price;
                    private int status;
                    private String title;
                    private String tk_rate;
                    private int type;
                    private int user_type;
                    private int volume;
                    private String zk_final_price;
                    private String zk_final_price_wap;

                    public String getClick_url() {
                        return click_url;
                    }

                    public void setClick_url(String click_url) {
                        this.click_url = click_url;
                    }

                    public String getCoupon_click_url() {
                        return coupon_click_url;
                    }

                    public void setCoupon_click_url(String coupon_click_url) {
                        this.coupon_click_url = coupon_click_url;
                    }

                    public String getCoupon_end_time() {
                        return coupon_end_time;
                    }

                    public void setCoupon_end_time(String coupon_end_time) {
                        this.coupon_end_time = coupon_end_time;
                    }

                    public String getCoupon_info() {
                        return coupon_info;
                    }

                    public void setCoupon_info(String coupon_info) {
                        this.coupon_info = coupon_info;
                    }

                    public int getCoupon_remain_count() {
                        return coupon_remain_count;
                    }

                    public void setCoupon_remain_count(int coupon_remain_count) {
                        this.coupon_remain_count = coupon_remain_count;
                    }

                    public String getCoupon_start_time() {
                        return coupon_start_time;
                    }

                    public void setCoupon_start_time(String coupon_start_time) {
                        this.coupon_start_time = coupon_start_time;
                    }

                    public int getCoupon_total_count() {
                        return coupon_total_count;
                    }

                    public void setCoupon_total_count(int coupon_total_count) {
                        this.coupon_total_count = coupon_total_count;
                    }

                    public String getEvent_end_time() {
                        return event_end_time;
                    }

                    public void setEvent_end_time(String event_end_time) {
                        this.event_end_time = event_end_time;
                    }

                    public String getEvent_start_time() {
                        return event_start_time;
                    }

                    public void setEvent_start_time(String event_start_time) {
                        this.event_start_time = event_start_time;
                    }

                    public String getItem_url() {
                        return item_url;
                    }

                    public void setItem_url(String item_url) {
                        this.item_url = item_url;
                    }

                    public long getNum_iid() {
                        return num_iid;
                    }

                    public void setNum_iid(long num_iid) {
                        this.num_iid = num_iid;
                    }

                    public String getPict_url() {
                        return pict_url;
                    }

                    public void setPict_url(String pict_url) {
                        this.pict_url = pict_url;
                    }

                    public String getReserve_price() {
                        return reserve_price;
                    }

                    public void setReserve_price(String reserve_price) {
                        this.reserve_price = reserve_price;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getTk_rate() {
                        return tk_rate;
                    }

                    public void setTk_rate(String tk_rate) {
                        this.tk_rate = tk_rate;
                    }

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }

                    public int getUser_type() {
                        return user_type;
                    }

                    public void setUser_type(int user_type) {
                        this.user_type = user_type;
                    }

                    public int getVolume() {
                        return volume;
                    }

                    public void setVolume(int volume) {
                        this.volume = volume;
                    }

                    public String getZk_final_price() {
                        return zk_final_price;
                    }

                    public void setZk_final_price(String zk_final_price) {
                        this.zk_final_price = zk_final_price;
                    }

                    public String getZk_final_price_wap() {
                        return zk_final_price_wap;
                    }

                    public void setZk_final_price_wap(String zk_final_price_wap) {
                        this.zk_final_price_wap = zk_final_price_wap;
                    }
                }
            }
        }
    }
}
