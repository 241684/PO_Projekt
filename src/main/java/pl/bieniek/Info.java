package pl.bieniek;

public class Info {
    private String operatingSystem;
    private String cpu;
    private String gpu;
    private String ram;

    public Info(String operatingSystem, String cpu, String gpu, String ram) {
        this.operatingSystem = operatingSystem;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
}
