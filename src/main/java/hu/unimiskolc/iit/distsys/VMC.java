package hu.unimiskolc.iit.distsys;

import hu.mta.sztaki.lpds.cloud.simulator.Timed;
import hu.mta.sztaki.lpds.cloud.simulator.iaas.PhysicalMachine;
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
		
		VirtualAppliance va = new VirtualAppliance("1", 1 , 0);
		
		ResourceConstraints rc = new ConstantConstraints(0.2 , 0.2 , 16);
		
		pm.localDisk.registerObject(va);
	    
		pm.requestVM(va, rc, pm.localDisk, 2); //pm bõl RC , va-vasource
		
		Timed.simulateUntilLastEvent();
	}

	public void indirectVMCreation() throws Exception{
		
	}

	public void migratedVMCreation() throws Exception{
		
	}
}
