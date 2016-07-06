package it.locandafienilicampiaro.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * This is an Utility class to manage Resources in easy way
 *
 */
public final class ResourceUtils {

	
    /**
     * The default encoding for the files
     */
    private static final String DEFAULT_ENCODING = "UTF8";

    /** Chiavi per le risorse applicative. */
    private static final String RESOURCE_SECTION_DROWABLE = "drawable";
    private static final String RESOURCE_SECTION_STRING = "string";
    private static final String RESOURCE_SECTION_ID = "id";
    private static final String RESOURCE_SECTION_LAYOUT = "layout";
    
    /*
     * Private constructor
     */
    private ResourceUtils() {
        throw new AssertionError("Never call this!!!");
    }

    
    /**
     * Restituisce l'identificativo numerico della risorsa drowable cercata.
     * @param context
     * @param resourceKey
     * @return
     */
    public static int getDrowableIdentifier(Application context, String resourceKey) {
    	 return getResourceIdentifier(context.getResources(), 
    			 resourceKey, RESOURCE_SECTION_DROWABLE, 
    			 context.getPackageName());
    }
    
    /**
     * Restituisce il valore della risorsa string cercata.
     * @param context
     * @param resourceKey
     * @return
     */
    public static String getStringIdentifier(Application context, int resourceKey) {
    	
    	int resourceId = getResourceIdentifier(context.getResources(), 
    			String.valueOf(resourceKey), RESOURCE_SECTION_STRING, 
    			context.getPackageName());
    	
    	return context.getString(resourceId);
    }
    
    /**
     * Restituisce il valore della risorsa string cercata.
     * @param context
     * @param resourceKey
     * @return
     */
    public static String getStringIdentifier(Application context, String resourceKey) {
    	
    	int resourceId = getResourceIdentifier(context.getResources(), 
    			resourceKey, RESOURCE_SECTION_STRING, 
    			context.getPackageName());
    	
    	return context.getString(resourceId);
    }

    /**
     * Restituisce l'identificativo numerico della risorsa string cercata.
     * @param context
     * @param resourceKey
     * @return
     */
    public static int getResourceIdentifierByName(Application context, String resourceKey) {
    	
    	int resourceId = getResourceIdentifier(context.getResources(), 
   			 resourceKey, RESOURCE_SECTION_ID, 
   			 context.getPackageName());
    	
   	 	return resourceId;
    }
    
    /**
     * Restituisce l'identificativo numerico della risorsa string cercata.
     * @param context
     * @param resourceKey
     * @return
     */
    public static int getLayoutByName(Application context, String resourceKey) {
    	
    	int resourceId = getResourceIdentifier(context.getResources(), 
   			 resourceKey, RESOURCE_SECTION_LAYOUT, 
   			 context.getPackageName());
    	
   	 	return resourceId;
    }
    
    /**
     * Restituisce l'identificativo numerico della risorsa cercata.
     * @param resources
     * @param resourceKey
     * @param resourceLocation
     * @param appPackage
     * @return
     */
    private static int getResourceIdentifier(Resources resources, 
    		String resourceKey, String resourceLocation, String appPackage) {
    	return resources.getIdentifier(
    			resourceKey, resourceLocation, appPackage);
    }
    
    
    
    /**
     * Return the content of a raw resource as a String given the encoding
     *
     * @param context  The Context reference
     * @param encoding The encoding to use for reading
     * @param rawId    The id for the raw resource
     * @return The raw Resource as a String
     * @throws java.io.IOException In case of error reading from the Stream
     * @throws java.io.UnsupportedEncodingException
     *                             In case of wrong encoding
     */
    public static String getRawAsString(Application context, String encoding, int rawId) 
    		throws UnsupportedEncodingException, IOException {
    	try {
    		Resources resources = context.getResources();
			InputStream is = resources.openRawResource(rawId);
			String result = IOUtils.toString(is, encoding);
			return result;
    	} catch(Exception ex) {
    		System.err.println(ex);
    		ex.printStackTrace();
    		return ""; 
    	}
    }

    /**
     * Return the content of an assets file as a String given the encoding
     *
     * @param context    The Context reference
     * @param encoding   The encoding to use for reading
     * @param assetsFile The file of the assets to read
     * @return The raw Resource as a String
     * @throws java.io.IOException In case of error reading from the Stream
     * @throws java.io.UnsupportedEncodingException
     *                             In case of wrong encoding
     */
    public static String getAssetsAsString(Context context, String encoding, String assetsFile) 
    		throws UnsupportedEncodingException, IOException {
        InputStream is = context.getAssets().open(assetsFile);
        String result = IOUtils.toString(is, encoding);
        return result;
    }

    /**
     * Return the content of a raw resource as a String using UTF-8 encoding
     *
     * @param context The Context reference
     * @param rawId   The id for the raw resource
     * @return The raw Resource as a String
     * @throws java.io.IOException In case of error reading from the Stream
     * @throws java.io.UnsupportedEncodingException
     *                             In case of wrong encoding
     */
    public static String getRawAsString(Application context, int rawId) 
    		throws UnsupportedEncodingException, IOException {
        return getRawAsString(context, DEFAULT_ENCODING, rawId);
    }

    /**
     * Return the content of a raw resource as a String using UTF-8 encoding
     *
     * @param context    The Context reference
     * @param assetsFile The file of the assets to read
     * @return The raw Resource as a String
     * @throws java.io.IOException In case of error reading from the Stream
     * @throws java.io.UnsupportedEncodingException
     *                             In case of wrong encoding
     */
    public static String getAssetsAsString(Context context, String assetsFile) 
    		throws UnsupportedEncodingException, IOException {
        return getAssetsAsString(context, DEFAULT_ENCODING, assetsFile);
    }

    ;


    /**
     * Return the content of a raw resource as a String given the encoding. The String can contains
     * placeholders as a printf
     *
     * @param context  The Context reference
     * @param encoding The encoding to use for reading
     * @param rawId    The id for the raw resource
     * @param args     The args if present
     * @return The raw Resource as a String
     * @throws java.io.IOException In case of error reading from the Stream
     * @throws java.io.UnsupportedEncodingException
     *                             In case of wrong encoding
     */
    public static String getRawAsEvaluatedFormat(Application context, String encoding, int rawId, Object... args) 
    		throws UnsupportedEncodingException, IOException {
        String strFormat = getRawAsString(context, encoding, rawId);
        if (strFormat != null) {
            return String.format(strFormat, args);
        } else {
            return null;
        }
    }

    /**
     * Return the content of a raw resource as a String as URF-8 encoding. The String can contains
     * placeholders as a printf
     *
     * @param context The Context reference
     * @param rawId   The id for the raw resource
     * @param args    The args if present
     * @return The raw Resource as a String
     * @throws java.io.IOException In case of error reading from the Stream
     * @throws java.io.UnsupportedEncodingException
     *                             In case of wrong encoding
     */
    public static String getRawAsEvaluatedFormat(Application context, int rawId, Object... args) 
    		throws UnsupportedEncodingException, IOException {
        return getRawAsEvaluatedFormat(context, DEFAULT_ENCODING, rawId, args);
    }

}
