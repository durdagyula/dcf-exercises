package hu.unimiskolc.iit.distsys;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.IaaSService;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.VirtualMachine;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine.ResourceAllocation;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.constraints.AlterableResourceConstraints;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.constraints.ConstantConstraints;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.constraints.ResourceConstraints;
import hu.mta.sztaki.lpds.cloud.simulator.io.VirtualAppliance;

public class VMC extends ExercisesBase implements VMCreationApproaches {
	
	public void directVMCreation() throws Exception{
		PhysicalMachine pm = getNewPhysicalMachine();
		
		pm.turnon();
		
		Timed.simulateUntilLastEvent();
		
		VirtualAppliance va = new VirtualAppliance("1", 1 , 0);
		
		ResourceConstraints rc = new ConstantConstraints(0.2 , 0.2 , 16);
		
		pm.localDisk.registerObject(va);
	    
		pm.requestVM(va, rc, pm.localDisk, 2); //pm bõl RC , va-vasource
		
		Timed.simulateUntilLastEvent();
		
	}

	public void twoPhaseVMCreation() throws Exception{
		PhysicalMachine pm = getNewPhysicalMachine();
		
		pm.turnon();
		
		Timed.simulateUntilLastEvent();
		
		VirtualAppliance va = new VirtualAppliance("VirtualA", 1, 0);
		
		pm.localDisk.registerObject(va);
		
		ResourceAllocation ra_1 = pm.allocateResources(new AlterableResourceConstraints(0.2 , 0.2 , 16), true, 10);
		
		ResourceAllocation ra_2 = pm.allocateResources(new AlterableResourceConstraints(0.2 , 0.2 , 16), true, 10);
		
		VirtualMachine vm1 = new VirtualMachine(va);
		VirtualMachine vm2 = new VirtualMachine(va);
		
		pm.deployVM(vm1, ra_1, pm.localDisk);
		pm.deployVM(vm2, ra_2, pm.localDisk);
		
		Timed.simulateUntilLastEvent();
	}

	public void indirectVMCreation() throws Exception{
		
		IaaSService iaas = ExercisesBase.getNewIaaSService();
		
		PhysicalMachine pm = ExercisesBase.getNewPhysicalMachine();
		
		pm.turnon();
		
		iaas.registerHost(pm);
		iaas.registerRepository(pm.localDisk);
		
		Timed.simulateUntilLastEvent();
		
		VirtualAppliance va = new VirtualAppliance("VirtualA", 1, 0);
		
		pm.localDisk.registerObject(va);
		
		iaas.requestVM(va, new AlterableResourceConstraints(0.2 , 0.2 , 16), pm.localDisk, 2);
		
		Timed.simulateUntilLastEvent();
		
	}

	public void migratedVMCreation() throws Exception{
		
		PhysicalMachine pm1 = ExercisesBase.getNewPhysicalMachine();
		PhysicalMachine pm2 = ExercisesBase.getNewPhysicalMachine();
		
		pm1.turnon();
		pm2.turnon();
		
		Timed.simulateUntilLastEvent();
		
		VirtualAppliance va = new VirtualAppliance("VA1", 1, 0);
		
		pm1.localDisk.registerObject(va);
		pm2.localDisk.registerObject(va);
				
		VirtualMachine vm = pm1.requestVM(va, new AlterableResourceConstraints(0.2 , 0.2 , 16), pm1.localDisk, 1)[0];
		
		Timed.simulateUntilLastEvent();	
		
		pm2.allocateResources(new AlterableResourceConstraints(0.2 , 0.2 , 16), false, 10);
		
		pm1.migrateVM(vm, pm2);
		
			
		Timed.simulateUntilLastEvent();
	}
}
