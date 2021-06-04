package pl.bieniek;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

public class RAM {
    long total;

    public RAM(){
        SystemInfo info = new SystemInfo();
        HardwareAbstractionLayer hal = info.getHardware();
        GlobalMemory RAM = hal.getMemory();
        total = RAM.getTotal()/1000000000;
    }
}