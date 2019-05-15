package io.dimitris.markingmate.util;

import java.io.File;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eml.EmlModule;

import io.dimitris.markingmate.MarkingmatePackage;

public class Merger {
	
	public static void main(String[] args) throws Exception {
		File m1 = new File("/Users/dkolovos/Desktop/markingmate-sepr/sepr1819-assessment2-dimitris.model");
		File m2 = new File("/Users/dkolovos/Desktop/markingmate-sepr/sepr1819-assessment2-javier.model");
		//File m3 = new File("/Users/dkolovos/Desktop/markingmate-sepr/sepr1819-assessment2-richard.model");
		File out = new File("/Users/dkolovos/Desktop/markingmate-sepr/sepr1819-assessment2-merged.model");
		
		EPackage.Registry.INSTANCE.put(MarkingmatePackage.eINSTANCE.getNsURI(), MarkingmatePackage.eINSTANCE);
		new Merger().merge(new File[] {m1, m2}, out);
		
	}
	
	public void merge(File[] inputs, File output) throws Exception {
		
		EmfModel left = null;
		EmfModel target = createTarget(output);
		
		for (int i=1;i<inputs.length;i++) {
			
			EclModule eclModule = new EclModule();
			eclModule.parse(new File("resources/markingmate.ecl"));
			
			if (left == null) {
				left = new EmfModel();
				left.setName("Left");
				left.getAliases().add("Source");
				left.setMetamodelUri(MarkingmatePackage.eINSTANCE.getNsURI());
				left.setModelFile(inputs[0].getAbsolutePath());
				left.setReadOnLoad(true);
				left.setStoredOnDisposal(false);
				left.load();
			}
			
			EmfModel right = new EmfModel();
			right.setName("Right");
			right.getAliases().add("Source");
			right.setMetamodelUri(MarkingmatePackage.eINSTANCE.getNsURI());
			right.setModelFile(inputs[i].getAbsolutePath());
			right.setReadOnLoad(true);
			right.setStoredOnDisposal(false);
			right.load();
			
			eclModule.getContext().getModelRepository().addModel(left);
			eclModule.getContext().getModelRepository().addModel(right);
			
			eclModule.execute();
			
			EmlModule emlModule = new EmlModule();
			emlModule.parse(new File("resources/markingmate.eml"));
			
			emlModule.getContext().setMatchTrace(eclModule.getContext().getMatchTrace());
			emlModule.getContext().getModelRepository().addModel(left);
			emlModule.getContext().getModelRepository().addModel(right);
			emlModule.getContext().getModelRepository().addModel(target);
			
			emlModule.execute();
			
			left.dispose();
			right.dispose();
			
			left = target;
			target = createTarget(output);
		}
		
	}
	
	public EmfModel createTarget(File output) throws Exception {
		EmfModel target = new EmfModel();
		target.setName("Target");
		target.setMetamodelUri(MarkingmatePackage.eINSTANCE.getNsURI());
		target.setModelFile(output.getAbsolutePath());
		target.setReadOnLoad(false);
		target.setStoredOnDisposal(true);
		target.load();
		return target;
	}
	
}
