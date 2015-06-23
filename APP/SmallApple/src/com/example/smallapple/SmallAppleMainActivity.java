package com.example.smallapple;

import java.io.IOException; 
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.util.UUID;

import android.R.integer;
import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast; 
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;


public class SmallAppleMainActivity extends Activity {

	private final String TAG = "SmallAppleQuadcopter";
	private Button CONNECT 	;   // 连接   0x01
//	private Button TAKEOFF	;	// 起飞   
//	private Button STOP 	; 	// 停止
	private Button FORWARD 	;	// 前进   0x03
	private Button BACKWARD	;	// 后退   0x06
	private Button LEFT		;	// 向左   0x04
	private Button RIGHT	;	// 向右   0x05
	private Button RISE		;	// 上升   0x07
	private Button FALLING	;	// 下降  0x08
	private static byte[] QuadCopter_cmd= {0x3a,01,00,00,00,00,00,0x0d,0x0a};        
	
	private BluetoothAdapter mBluetoothAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outputStream = null;
	private InputStream  inputStream = null;
	private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") ; // 蓝牙串口模式的UUID
	private static String QuadCopter_Addr = "00:12:02:22:06:61";    // 连接设备的地址
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
/**     button start   **/        
        CONNECT = ( Button )this.findViewById(R.id.button_connect);
        CONNECT.setOnClickListener(QuadCopter_ClickListener);                   
        
        FORWARD = (Button)this.findViewById(R.id.imageButton_forward);
        FORWARD.setOnClickListener(QuadCopter_ClickListener);
        
        BACKWARD = (Button)this.findViewById(R.id.imageButton_backward);
        BACKWARD.setOnClickListener(QuadCopter_ClickListener);
        
        LEFT = (Button)this.findViewById(R.id.imageButton_left);
        LEFT.setOnClickListener(QuadCopter_ClickListener);
        
        RIGHT = (Button)this.findViewById(R.id.imageButton_right);
        RIGHT.setOnClickListener(QuadCopter_ClickListener);
        
        RISE = (Button)this.findViewById(R.id.imageButton_rise);
        RISE.setOnClickListener(QuadCopter_ClickListener);
        
        FALLING = (Button)this.findViewById(R.id.imageButton_falling);
        FALLING.setOnClickListener(QuadCopter_ClickListener);
 /** 	button end		**/
 
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();             //get default Adapter 
        if( mBluetoothAdapter == null )
        {
        	Log.i(TAG," Bluetooth is not availiable ") ;
        	finish();
        	return ;
         }
         if( !mBluetoothAdapter.isEnabled() )
         {
        	 Log.i(TAG, "please enable your Bluetooth and re-run ");
        	 finish();
        	 return ;
         }
        
         Log.i(TAG,"onCreat method in executed");
    }
    
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
        Log.i(TAG,"onStart method in executed");
    }
    
   @Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
        Log.i(TAG,"onRestart method in executed");
	}
   @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		/**  获取远程 蓝牙 Bluetooth Device name  **/
		BluetoothDevice QuadCopter_device = mBluetoothAdapter.getRemoteDevice(QuadCopter_Addr) ;
		if( mBluetoothAdapter == null )
		{
			Log.i(TAG,"please connect remote device ");
			finish();
			return ;
		}
		
		/**  获取远程通讯socket   **/
		try {
			
			btSocket = QuadCopter_device.createRfcommSocketToServiceRecord(BT_UUID);
		
		} catch (IOException e) {
			// TODO: handle exception
		}
		
		/**  关闭descovery 功能      **/
		mBluetoothAdapter.cancelDiscovery() ;
		
		/**  连接 scoket   **/
		try {
			btSocket.connect() ;
		} catch (IOException e) {
			// TODO: handle exception
			try {
				btSocket.close();	
			} catch (IOException e2) {
				// TODO: handle exception
			}
		}
		
		/**  获取蓝牙输出流              **/
		try {
			outputStream = btSocket.getOutputStream();
		} catch (IOException e) {
			// TODO: handle exception
		}
		
        Log.i(TAG,"onResume method in executed");
	}
   	
   	@Override
   	protected void onPause() {
   		// TODO Auto-generated method stub
   		super.onPause();
        Log.i(TAG,"onPause method in executed");
   	}
   	
   	@Override
   	protected void onStop() {
   		// TODO Auto-generated method stub
   		super.onStop();
        Log.i(TAG,"onStop method in executed");
   	}
   	
   	@Override
   	protected void onDestroy() {
   		// TODO Auto-generated method stub
   		super.onDestroy();
        Log.i(TAG,"onDestroy method in executed");
   	}
   	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Log.i(TAG,"onCreateOptionsMenu method in executed");
        return true;
    }
    
    /**
     * 
     * @author jy.chen
     * 触摸飞控按键处理
     */
    View.OnClickListener QuadCopter_ClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch( arg0.getId() ){
			case R.id.button_connect :
				Log.i(TAG,"onclick connect button");
				QuadCopter_cmd[2] = 0x01 ;

				break;
			
			case R.id.imageButton_forward :
				Log.i(TAG,"onclick forard button");
				QuadCopter_cmd[2] = 0x03 ;
				break;
			
			case R.id.imageButton_backward :
				Log.i(TAG,"onclick backward button");
				QuadCopter_cmd[2] = 0x06 ;
				break;
				
			case R.id.imageButton_left :
				Log.i(TAG,"onclick left button");
				QuadCopter_cmd[2] = 0x04 ;
				break;
			case R.id.imageButton_right :
				Log.i(TAG,"onclick right button");
				QuadCopter_cmd[2] = 0x05 ;
				break;
			case R.id.imageButton_rise :
				Log.i(TAG,"onclick rise button");
				QuadCopter_cmd[2] = 0x07 ;
				break;
			case R.id.imageButton_falling :
				Log.i(TAG,"onclick falling button");
				QuadCopter_cmd[2] = 0x08 ;
				break;
				
			default :
				break ;
			}
			SumCheck(QuadCopter_cmd,5);
			try {
				outputStream.write(QuadCopter_cmd, 0, QuadCopter_cmd.length) ;
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	};
	protected void SumCheck(byte arg[] ,int indx ) {
		int i = 0 ;
		byte sum = 0;

		for( i = 0 ; i < indx ; i++)
		{
			sum += arg[i];
		}
		arg[indx] = (byte)(sum & 0xF0);
		arg[indx+1] = (byte)(sum & 0xF);
	}   
}
