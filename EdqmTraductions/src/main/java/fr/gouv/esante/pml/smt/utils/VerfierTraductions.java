package fr.gouv.esante.pml.smt.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.EntitySearcher;

public class VerfierTraductions {
	
	
	private static OWLOntologyManager manager = null;
	private static OWLOntology onto = null;
	
	private static OWLDataFactory fact = null;
	private static InputStream input = null;
	
	private static String sourceOWLFile = PropertiesUtil.getProperties("sourceOWLFile");
	
	public static List<String> listTermsBylabelFr = new ArrayList<String>();
	
	
	private static OWLAnnotationProperty rdfsLabel  = null;

	//public static void main(String[] args) throws OWLOntologyCreationException, FileNotFoundException {
		
		public static  void  checkLabelFr() throws IOException, OWLOntologyCreationException {	
		
		
        manager = OWLManager.createOWLOntologyManager();
		
		onto = manager.createOntology(IRI.create("http://data.esante.gouv.fr/coe/standardterms#"));

		fact = onto.getOWLOntologyManager().getOWLDataFactory();
		
		rdfsLabel =  fact.getOWLAnnotationProperty(fr.gouv.esante.pml.smt.utils.OWLRDFVocabulary.RDFS_LABEL.getIRI());
		
		getOntologieCore();
		
		 for (OWLAxiom axioms : onto.getAxioms()) {
	        	
	      	  
	      	  
	      	  
	      	  
	      	  axioms.accept(new OWLAxiomVisitor() {
	  	    	  
	  	    	  
	   	    	 public void visit(OWLSubClassOfAxiom arg0) {
	   	              
	   	    		 

	   	            }
	   	    	  
	   	    	  
	   	    	  
	   	    	  public void visit(OWLDeclarationAxiom arg0) {
	   	    		  
	   	    		     OWLEntity annotation1 = arg0.getEntity();
	   	    		
	   	    		  
	                     //System.out.println( annotation1.toString());
	                     
	                     Stream<OWLAnnotation> stream = EntitySearcher.getAnnotations(annotation1, onto, rdfsLabel);
	                     
	                    List<String> tab = new ArrayList<String>();
	                     
	                     if(stream.count()==2) {
	                    	 
	                    	// System.out.println("**** "+annotation1.toString());
	                    	 
	                    	 listTermsBylabelFr.add(annotation1.toString());
	                     }
	                     
	                     
	                     //stream.forEach(ax -> {
	             	    	  
	                    	// OWLAnnotationValue val = ax.getValue();
	                    	// if (val instanceof OWLLiteral) {
	                    	       
	                    	  //  String label = ((OWLLiteral) val).getLiteral();
	                    	   // String lang = ((OWLLiteral) val).getLang();   
	                    	    
	                    	   // System.out.println(annotation1.toString()+ "***** "+ label + " *****"+lang);
	                    	   // if(("fr").equals(lang))
	                    	     // tab.add("fr**");
	                    	        
	                    	// }
	             	  	 
	         	    	//} );
	                    	 
	                    	 
	                     
	    	    		 
	   	            }
	   	    	  
	      	  });
			
	      	  
	        }
		

	}
	
	
	private static void getOntologieCore() throws FileNotFoundException, OWLOntologyCreationException {
    	//logger.info("importer le core de la terminologie");
		input = new FileInputStream(sourceOWLFile);
	    manager = OWLManager.createOWLOntologyManager();
	    OWLOntology core = manager.loadOntologyFromOntologyDocument(input);
	    
	    
	    core.axioms().
	    forEach(ax -> {
	    	manager.applyChange(new AddAxiom(onto, ax));
	 
	    	});
	    //logger.info("fin import du core de la terminologie");
	}

}
