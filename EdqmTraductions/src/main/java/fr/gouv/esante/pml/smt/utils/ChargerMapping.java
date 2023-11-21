package fr.gouv.esante.pml.smt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ChargerMapping {
	
	public static HashMap<String, List<String>> listConceptsEdqm = new HashMap<String, List<String>>();
	public static String URI_Edqm = "http://data.esante.gouv.fr/coe/standardterms";

	public static  void  chargeExcelConceptToList(final String xlsxRihnFileName) throws IOException {
		
	// public static  void  main(String[] args) throws IOException {
	
		
        //String xlsxRihnFileName = PropertiesUtil.getProperties("edqmTraduction");
		
		FileInputStream file = new FileInputStream(new File(xlsxRihnFileName));
		
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		
		XSSFSheet sheet_Fichier_Definition = workbook.getSheet("fichier définitions");
		
		XSSFSheet sheet_Fichier_A_Valider = workbook.getSheet("fichier à valider");
		
		Iterator<Row> rowIterator_Definition = sheet_Fichier_Definition.iterator();
		
		rowIterator_Definition.next();
		
		Iterator<Row> rowIterator_Label = sheet_Fichier_A_Valider.iterator();
		rowIterator_Label.next();
		
		// List<String> listedonnees= new ArrayList<>();
		
		while (rowIterator_Definition.hasNext()) {
			 
			List<String> listedonnees= new ArrayList<>();
			 Row row = rowIterator_Definition.next();
	    	 Cell code = row.getCell(2); //get Ref Code
		     Cell definition_traduction = row.getCell(8); // get Libelle
		    
			   
		   if(code != null) { 
		    // System.out.println("** "+c1.getStringCellValue());
		     
		     
		        
		        	//System.out.println("*** "+c2.getStringCellValue());	
		         
			   listedonnees.add(definition_traduction.getStringCellValue()); //0
			   listedonnees.add("");//1
		        
		     if("EDQM".equals(code.getStringCellValue()))
		      listConceptsEdqm.put(URI_Edqm, listedonnees);
		     else
		       listConceptsEdqm.put(URI_Edqm+"/"+code.getStringCellValue(), listedonnees);	 
		     
		    }
		     
		  
		     
		     
		   
		}
		
		
		
		
		while (rowIterator_Label.hasNext()) {
			 
			//List<String> listedonnees= new ArrayList<>();
			 Row row = rowIterator_Label.next();
	    	 Cell code = row.getCell(3); //get Ref Code
		     Cell label_traduction = row.getCell(6); // get Libelle
			   
		   if(code != null) { 
		    //  System.out.println("** "+URI.getStringCellValue());
		     
		     
		        if(label_traduction != null) {
		        	//System.out.println("*** "+c2.getStringCellValue());	
		           //listedonnees.add(c2.getStringCellValue());
		           if(listConceptsEdqm.containsKey(URI_Edqm+"/"+code.getStringCellValue()))	
		        	 if("EDQM".equals(code.getStringCellValue()))  
		              listConceptsEdqm.get(URI_Edqm).add(1, label_traduction.getStringCellValue());
		        	 else
		        		 listConceptsEdqm.get(URI_Edqm+"/"+code.getStringCellValue()).add(1, label_traduction.getStringCellValue());
		           else {
		        	   
		        	  // System.out.println("*** "+URI.getStringCellValue());
		        	   List<String> listedonnees= new ArrayList<>();
		        	   listedonnees.add(""); //0
					   listedonnees.add(label_traduction.getStringCellValue());//1
				        
					   if("EDQM".equals(code.getStringCellValue())) 
				         listConceptsEdqm.put(URI_Edqm, listedonnees);
					   else
						 listConceptsEdqm.put(URI_Edqm+"/"+code.getStringCellValue(), listedonnees);
		           }
		        	   
		        }
		        else {
		        	
		        	if(listConceptsEdqm.containsKey(URI_Edqm+"/"+code.getStringCellValue()))	
		        	{
		        		//System.out.println("** "+URI.getStringCellValue());
		        	   if("EDQM".equals(code.getStringCellValue())) 	
		        		 listConceptsEdqm.get(URI_Edqm).add(1, "");
		        	   else
		        		 listConceptsEdqm.get(URI_Edqm+"/"+code.getStringCellValue()).add(1, ""); 
		        	}
		        	else {
		        		  //System.out.println("*** "+URI.getStringCellValue());
		        		   List<String> listedonnees= new ArrayList<>();
			        	   listedonnees.add(""); //0
						   listedonnees.add("");//1
					    
					       listConceptsEdqm.put(URI_Edqm+"/"+code.getStringCellValue(), listedonnees);
		        		
		        	}
		        		
		          }
		     
		     //listConceptsEdqm.put(URI, listedonnees);
		     
		    }
		     
		  
		     
		     
		   
		}

		
		
	    System.out.println("0040: "+listConceptsEdqm.get("http://data.esante.gouv.fr/coe/standardterms/0040").get(0));
	    System.out.println("0040: "+listConceptsEdqm.get("http://data.esante.gouv.fr/coe/standardterms/0040").get(1));
	     //System.out.println("Preferred 50081000: "+listConceptsEdqm.get("50081000").get(0));
	    // System.out.println("Alias: "+liste.get(1));
	    // System.out.println("Translation FR: "+liste.get(2));
	    // System.out.println("Translation EN : "+liste.get(3));
		//return listConceptsEma;
	    

	}

}
