package com.base.feima.baseproject.model.version;


import com.base.feima.baseproject.model.ResultModel;

public class VersionResult extends ResultModel {

    /**
     * msg :
     * code : 2000
     * data : {"updateInfo":"������Ϣ","download":"���ص�ַ","version":"�汾������V2.0.0"}
     */

    private VersionDataEntity data;



    public void setData(VersionDataEntity data) {
        this.data = data;
    }



    public VersionDataEntity getData() {
        return data;
    }

    public class VersionDataEntity {
        /**
         * updateInfo : ������Ϣ
         * download : ���ص�ַ
         * version : �汾������V2.0.0
         */
        private String updateInfo;
        private String download;
        private String version;
        private String versionCode;

        public void setUpdateInfo(String updateInfo) {
            this.updateInfo = updateInfo;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUpdateInfo() {
            return updateInfo;
        }

        public String getDownload() {
            return download;
        }

        public String getVersion() {
            return version;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }
    }
}