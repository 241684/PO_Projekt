package pl.bieniek;

import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;

import java.util.List;

public class SYSTEM {
    String manufacturer;
    String family;
    OperatingSystem.OSVersionInfo ver;

    public SYSTEM(){
        SystemInfo info = new SystemInfo();
        OperatingSystem os = info.getOperatingSystem();
        manufacturer = os.getManufacturer();
        family = os.getFamily();
        ver = info.getOperatingSystem().getVersionInfo();
    }
}
