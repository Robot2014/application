package com.example.smallapple;

import java.util.UUID;

import javax.security.auth.PrivateCredentialPermission;

import android.Manifest.permission;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.text.NoCopySpan.Concrete;
import android.util.Log;

public class BluetoothService {
	
	private static final String  TAG = "BluetoothService" ;
	private static final boolean Debug = true ;
	private static final UUID BT_SERIAL_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") ; // 蓝牙串口模式的U	
	
	private final BluetoothAdapter mAdapter ;
	private final Handler mHandler ;
	private int mState ;                       // BT state 
	
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

    /**
     * Constructor. Prepares a new BluetoothSevice session.
     * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
     */
    public BluetoothService(Context context , Handler handler) {
    	mAdapter = BluetoothAdapter.getDefaultAdapter();
    	mState = STATE_NONE ;
    	mHandler = handler ;
    }
 
    /**
     * Set the current state of the chat connection
     * @param state  An integer defining the current connection state
     */    
    private  synchronized void setState(int state) {
    if(Debug) Log.d(TAG," setState" + mState + " --> " + state) ; 	
    //	mHandler.sendMessage( state);
	}
    
    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }
    
    public synchronized void start(){
    	if(Debug) Log.d(TAG, "BluetoothService start") ;
    	
    }
    
    
}
