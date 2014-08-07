package de.itarchitecture.networkswitcher;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

public class NetworkSwitcherActivity extends Activity {
	private static boolean firstCall = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (firstCall)
		{
		firstCall = false;
		setContentView(R.layout.activity_network_switcher);
		WifiManager wifi =(WifiManager)getSystemService(Context.WIFI_SERVICE); 
		Context context = this.getBaseContext();
		if(wifi.isWifiEnabled())
		{
			wifi.setWifiEnabled(false);
			Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
			ComponentName cName = new ComponentName ("com.android.phone", "com.android.phone.Settings");
			startActivity(intent);
			finish();
		}
		else
		{
			setMobileDataEnabled(context, false);
			wifi.setWifiEnabled(true);
		}
		}
		System.exit(0);
	}
	private void setMobileDataEnabled(Context context, boolean enabled) {

        try {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(conman);
        final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);
        setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {

        }
    }
}
