package pl.bieniek;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

public class GPU {
    String Name;
    Long VRAM;

    public GPU(){
        SystemInfo info = new SystemInfo();
        HardwareAbstractionLayer hal = info.getHardware();
        List<GraphicsCard> gpu = hal.getGraphicsCards();
        Name = gpu.get(0).getName();
        VRAM = gpu.get(0).getVRam()/1000000000;
    }
}
