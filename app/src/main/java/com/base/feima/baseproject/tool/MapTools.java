package com.base.feima.baseproject.tool;

public class MapTools {
    /**
     * �жϰٶȵ�ͼ�Ƿ�λ�ɹ�
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
//	 * �ٶȵ�ͼ�������--��Ҫʹ�ã���Ҫ����ٶȵ�ͼ���jar��
//	 * @param context
//	 * @param targetLat
//	 * @param targetLon
//	 * @return
//	 */
//	public static String caculateDistance(Context context,String targetLat,String targetLon){
//		String result = "0.01";
//		try {
//			//�ҵ�γ��
//			double latF = Double.parseDouble(SharedUtil.getLat(context));
//			//�ҵľ���
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