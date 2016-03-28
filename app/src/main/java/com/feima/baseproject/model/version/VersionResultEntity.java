package com.feima.baseproject.model.version;


import com.feima.baseproject.model.ResultEntity;

public class VersionResultEntity extends ResultEntity {


    private VersionDataEntity data;



    public void setData(VersionDataEntity data) {
        this.data = data;
    }



    public VersionDataEntity getData() {
        return data;
    }

    public class VersionDataEntity {
        private String updateInfo;
        private String download;
        private String version;
        private String versionCode;
        private int forceUpdate = 0;//0是不强制，1是强制

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

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }
    }

}