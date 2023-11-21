package fr.gouv.esante.pml.smt.edqm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.util.AxiomSubjectProviderEx;

import fr.gouv.esante.pml.smt.utils.ChargerMapping;
import fr.gouv.esante.pml.smt.utils.PropertiesUtil;
import fr.gouv.esante.pml.smt.utils.SKOSVocabulary;
import fr.gouv.esante.pml.smt.utils.VerfierTraductions;

public class XLSXOWLTraduction {
	
	private static OWLOntologyManager manager = null;
	private static OWLOntology onto = null;
	
	private static OWLDataFactory fact = null;
	private static InputStream input = null;
	
	private static String OWLFile = PropertiesUtil.getProperties("edqmOWL");
	private static String coreOWLFile = PropertiesUtil.getProperties("edqmCoreOWL");
	
	private static String xlsxEdqmFileName = PropertiesUtil.getProperties("edqmTraduction");
	
	private static OWLAnnotationProperty skosDefinition  = null;
	private static OWLAnnotationProperty rdfsLabel  = null;
	

	public static void main(String[] args) throws OWLOntologyCreationException, OWLOntologyStorageException, IOException {
		

		//logger.info("Début de construction de la terminologie");
		
		
		ChargerMapping.chargeExcelConceptToList(xlsxEdqmFileName);
		
		VerfierTraductions.checkLabelFr();
		
		
		manager = OWLManager.createOWLOntologyManager();
		
		onto = manager.createOntology(IRI.create("http://data.esante.gouv.fr/coe/standardterms#"));

		fact = onto.getOWLOntologyManager().getOWLDataFactory();
		
		skosDefinition =  fact.getOWLAnnotationProperty(org.semanticweb.owlapi.vocab.SKOSVocabulary.DEFINITION.getIRI());
		rdfsLabel =  fact.getOWLAnnotationProperty(fr.gouv.esante.pml.smt.utils.OWLRDFVocabulary.RDFS_LABEL.getIRI());
		
		getOntologieCore();
		
		
		
		
		/*
		 * for (OWLAxiom axioms : onto.getAxioms()) {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * axioms.accept(new OWLAxiomVisitor() {
		 * 
		 * 
		 * public void visit(OWLSubClassOfAxiom arg0) {
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * public void visit(OWLDeclarationAxiom arg0) {
		 * 
		 * OWLEntity annotation1 = arg0.getEntity();
		 * 
		 * 
		 * //System.out.println( annotation1.toString());
		 * 
		 * Stream<OWLAnnotation> stream = EntitySearcher.getAnnotations(annotation1,
		 * onto, rdfsLabel);
		 * 
		 * 
		 * if(stream.count()==1) {
		 * 
		 * System.out.println("**** "+annotation1.toString());
		 * 
		 * }
		 * 
		 * }
		 * 
		 * });
		 * 
		 * 
		 * }
		 */
		
		   
		/*
		 * for (OWLAxiom axioms : onto.getAxioms()) {
		 * 
		 * 
		 * 
		 * 
		 * 
		 * axioms.accept(new OWLAxiomVisitor() {
		 * 
		 * 
		 * public void visit(OWLSubClassOfAxiom arg0) {
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * public void visit(OWLDeclarationAxiom arg0) {
		 * 
		 * OWLEntity annotation1 = arg0.getEntity();
		 * 
		 * 
		 * //System.out.println( annotation1.toString());
		 * 
		 * Stream<OWLAnnotation> stream = EntitySearcher.getAnnotations(annotation1,
		 * onto, skosDefinition);
		 * 
		 * List<String> tab = new ArrayList<String>();
		 * 
		 * if(stream.count()==0) {
		 * 
		 * // System.out.println(annotation1.toString()); }
		 * 
		 * 
		 * //stream.forEach(ax -> {
		 * 
		 * // OWLAnnotationValue val = ax.getValue(); // if (val instanceof OWLLiteral)
		 * {
		 * 
		 * // String label = ((OWLLiteral) val).getLiteral(); // String lang =
		 * ((OWLLiteral) val).getLang();
		 * 
		 * // System.out.println(annotation1.toString()+ "***** "+ label +
		 * " *****"+lang); // if(("fr").equals(lang)) // tab.add("fr**");
		 * 
		 * // }
		 * 
		 * //} );
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * });
		 * 
		 * 
		 * }
		 */
		
		
		ajoutTraductions();
		
		final OutputStream fileoutputstream = new FileOutputStream(OWLFile);
		final RDFXMLDocumentFormat ontologyFormat = new RDFXMLDocumentFormat();
		manager.saveOntology(onto, ontologyFormat, fileoutputstream);
		

	}
	
	
	public static void ajoutTraductions() {
		
		 for(String id: ChargerMapping.listConceptsEdqm.keySet()) {
			 
			 
			 
			    
			 
			 if(!ChargerMapping.listConceptsEdqm.get(id).get(0).isEmpty()) {
				 OWLClass  owlClass = fact.getOWLClass(IRI.create(id));
			   addLateralAxioms(skosDefinition, ChargerMapping.listConceptsEdqm.get(id).get(0), owlClass, "fr");
			 }
			 
			 if(!ChargerMapping.listConceptsEdqm.get(id).get(1).isEmpty()) {
				  // System.out.println("*** size "+VerfierTraductions.listTermsBylabelFr.size()) ;
				 OWLClass  owlClass1 = fact.getOWLClass(IRI.create(id));
				   if(! VerfierTraductions.listTermsBylabelFr.contains(owlClass1.toString())) {
					   //System.out.println(owlClass1.toString()) ;  
				     addLateralAxioms(rdfsLabel, ChargerMapping.listConceptsEdqm.get(id).get(1), owlClass1, "fr");
				   }else {
					   
					   //System.out.println(owlClass1.toString()) ; 
				   }
			 }
			 
		 }
		
	}
	
	 public static void addLateralAxioms(OWLAnnotationProperty prop, String val, OWLClass owlClass, String lang) {
		    final OWLAnnotation annotation =
		        fact.getOWLAnnotation(prop, fact.getOWLLiteral(val, lang));
		    final OWLAxiom axiom = fact.getOWLAnnotationAssertionAxiom(owlClass.getIRI(), annotation);
		    
		    manager.applyChange(new AddAxiom(onto, axiom));
		    
		    
		  }
		
		
	


	private static void getOntologieCore() throws FileNotFoundException, OWLOntologyCreationException {
    	//logger.info("importer le core de la terminologie");
		input = new FileInputStream(coreOWLFile);
	    manager = OWLManager.createOWLOntologyManager();
	    OWLOntology core = manager.loadOntologyFromOntologyDocument(input);
	    
	    
	    core.axioms().
	    forEach(ax -> {
	    	manager.applyChange(new AddAxiom(onto, ax));
	 
	    	});
	    //logger.info("fin import du core de la terminologie");
	}

}
