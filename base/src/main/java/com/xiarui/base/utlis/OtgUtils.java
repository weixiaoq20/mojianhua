package com.xiarui.base.utlis;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;

public  class OtgUtils {

    public static  void set53CGPIOEnabled(boolean enable) {
        FileOutputStream f = null;
        FileOutputStream f1 = null;
        try {
            Log.i("urovo", "enable:" + enable);
            f = new FileOutputStream("/sys/devices/soc/soc:sectrl/ugp_ctrl/gp_pogo_5v_ctrl/enable");
            f.write(enable ? "1".getBytes() : "0".getBytes());
            f1 = new FileOutputStream("/sys/devices/soc/soc:sectrl/ugp_ctrl/gp_otg_en_ctrl/enable");
            f1.write(enable ? "1".getBytes() : "0".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                try {
                    f.close();
                    f1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void set52OTGEnabled(boolean enable) {
        FileOutputStream f = null;
        try {
            f = new FileOutputStream("/sys/devices/soc/78db000.usb/dpdm_pulldown_enable");
            f.write(enable ? "otgenable".getBytes() : "otgdisable".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void set53GPIOEnabled(boolean enable) {
        FileOutputStream f = null;
        FileOutputStream f1 = null;
        try {
            Log.i("ubx", "set53GPIOcEnabled: " + enable);
            f = new FileOutputStream("/sys/devices/soc/c170000.serial/pogo_uart");
            f.write(enable ? "1".getBytes() : "0".getBytes());
            f1 = new FileOutputStream("/sys/devices/virtual/Usb_switch/usbswitch/function_otg_en");
            f1.write(enable ? "2".getBytes() : "0".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                try {
                    f.close();
                    f1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
