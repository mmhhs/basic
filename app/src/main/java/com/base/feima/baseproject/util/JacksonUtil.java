package com.base.feima.baseproject.util;

import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import java.io.IOException;

public class JacksonUtil
{
  private static final String TAG = "JacksonUtil";
  private static JacksonUtil instance;
  private ObjectMapper objectMapper;

  public JacksonUtil()
  {
    this.objectMapper = new ObjectMapper();
    this.objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public JacksonUtil(Inclusion paramInclusion)
  {
	  this();
  }

  public static JacksonUtil getInstance()
  {
    if (instance == null)
      instance = new JacksonUtil();
    return instance;
  }

  public synchronized <T> T  readValue(String paramString, TypeReference<T> paramTypeReference)
  {
	  T localObject1 = null;


      if (TextUtils.isEmpty(paramString)) {
    	  return null;
      }

        try
        {
        	localObject1 = this.objectMapper.readValue(paramString, paramTypeReference);
        }
        catch (JsonParseException localJsonParseException)
        {
          localJsonParseException.getMessage();
        }
        catch (JsonMappingException localJsonMappingException)
        {
          localJsonMappingException.getMessage();
        }
        catch (IOException localIOException)
        {
          localIOException.getMessage();
        }

        return localObject1;
  }

  public synchronized <T> T readValue(String paramString, Class<T> paramClass)
  {
	  T localObject1 = null;
      boolean bool = TextUtils.isEmpty(paramString);
      if (bool) {
    	  return null;
      }
        try
        {
        	localObject1 = this.objectMapper.readValue(paramString, paramClass);
          
        }
        catch (JsonParseException localJsonParseException)
        {
          localJsonParseException.getMessage();
        }
        catch (JsonMappingException localJsonMappingException)
        {
          localJsonMappingException.getMessage();
        }
        catch (IOException localIOException)
        {
          localIOException.getMessage();
        }
        catch (Exception localException)
        {
          localException.getMessage();
        }
        return localObject1;
  }


  public  synchronized String writeValueAsString(Object paramObject)
  {
	  if(paramObject == null) {
		  return null;
	  }
	  String result = null;
      try
      {
      		result = objectMapper.writeValueAsString(paramObject);
        
      }
      catch (JsonGenerationException localJsonParseException)
      {
        localJsonParseException.getMessage();
      }
      catch (JsonMappingException localJsonMappingException)
      {
        localJsonMappingException.getMessage();
      }
      catch (IOException localIOException)
      {
        localIOException.getMessage();
      }
      catch (Exception localException)
      {
        localException.getMessage();
      }
      
      return result;

  }
}
