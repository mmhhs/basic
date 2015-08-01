package com.base.feima.baseproject.tool;

public class MapTools {
    /**
     * 判断百度地图是否定位成功
     * @param longtitude
     * @return
     */
    public static boolean judgeLocation(double longtitude){
        boolean result = false;
        try {
            String lon = String.valueOf(longtitude);
            if(lon.contains("e")||lon.contains("E")){
                result=false;
            }else{
                result = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            return result;
        }
    }

//	/**
//	 * 百度地图计算距离--若要使用，需要导入百度地图相关jar包
//	 * @param context
//	 * @param targetLat
//	 * @param targetLon
//	 * @return
//	 */
//	public static String caculateDistance(Context context,String targetLat,String targetLon){
//		String result = "0.01";
//		try {
//			//我的纬度
//			double latF = Double.parseDouble(SharedUtil.getLat(context));
//			//我的经度
//			double lonF = Double.parseDouble(SharedUtil.getLng(context));
//			double lat = Double.parseDouble(targetLat);
//			double lon = Double.parseDouble(targetLon);
//			LatLng p1 = new LatLng(latF,lonF);
//			LatLng p2 = new LatLng(lat,lon);
//			double distance = DistanceUtil.getDistance(p1, p2);
//			result = storeTwoPosition(distance/1000);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}finally{
//			return ""+result;
//		}
//
//	}
}