package pl.bieniek;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class CPU {
    CentralProcessor.ProcessorIdentifier name;
    double freq;
    public CPU(){
        SystemInfo info = new SystemInfo();
        HardwareAbstractionLayer hal = info.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        name = cpu.getProcessorIdentifier();
        freq = (double)cpu.getMaxFreq()/1000000000;
    }
}
