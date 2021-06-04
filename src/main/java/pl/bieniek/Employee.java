package pl.bieniek;

public class Employee {
    private String name;
    private String os;
    private String cpu;
    private String gpu;
    private String ram;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, String os, String cpu, String gpu, String ram) {
        this.name = name;
        this.os = os;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getOs() {
        return os;
    }

    public void setOs(String os) { this.os = os; }

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
