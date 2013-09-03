package mtechproject.filesysmonitor;

import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.*;


import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class PerformanceMonitor {

    private static Sigar sigar = new Sigar();

    public static void getInformationsAboutMemory() {
        System.out.println("**************************************");
        System.out.println("*** Informations about the Memory: ***");
        System.out.println("**************************************\n");

        Mem mem = null;
        Cpu cpu = null;
        try {
            mem = sigar.getMem();
            cpu = sigar.getCpu();
        } catch (SigarException se) {
            se.printStackTrace();
        }

        System.out.println("Actual total free system memory: "
                + mem.getActualFree() / 1024 / 1024+ " MB");
        System.out.println("Actual total used system memory: "
                + mem.getActualUsed() / 1024 / 1024 + " MB");
        System.out.println("Total free system memory ......: " + mem.getFree()
                / 1024 / 1024+ " MB");
        System.out.println("System Random Access Memory....: " + mem.getRam()
                + " MB");
        System.out.println("Total system memory............: " + mem.getTotal()
                / 1024 / 1024+ " MB");
        System.out.println("Total used system memory.......: " + mem.getUsed()
                / 1024 / 1024+ " MB");

        System.out.println("\n**************************************\n");
        System.out.println("Information about CPU Usage:");
        System.out.println("idle: " + cpu.getIdle());//get overall CPU idle
        System.out.println("irq: " + cpu.getIrq());
        System.out.println("nice: " + cpu.getNice());
        System.out.println("soft irq: " + cpu.getSoftIrq());
        System.out.println("stolen: " + cpu.getStolen());
        System.out.println("sys: " + cpu.getSys());
        System.out.println("total: " + cpu.getTotal());
        System.out.println("user: " + cpu.getUser());
        System.out.println();
        
        CpuPerc perc = null;
		try {
			perc = sigar.getCpuPerc();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("overall CPU usage");
        System.out.println("system idle: " + perc.getIdle());//get current CPU idle rate
        System.out.println("conbined: "+ perc.getCombined());//get current CPU usage
        
        CpuPerc[] cpuPercs = null;
		try {
			cpuPercs = sigar.getCpuPercList();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("each CPU usage");
        for (CpuPerc cpuPerc : cpuPercs) {
        System.out.println("system idle: " + cpuPerc.getIdle());//get current CPU idle rate
        System.out.println("conbined: "+ cpuPerc.getCombined());//get current usage
        System.out.println();
        }


    }

    public static void main(String[] args) throws Exception{

                getInformationsAboutMemory();

                }

}