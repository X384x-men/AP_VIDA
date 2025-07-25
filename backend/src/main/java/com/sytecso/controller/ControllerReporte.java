package com.sytecso.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.DocumentException;
import com.sytecso.component.CustomContext;
import com.sytecso.component.EventMessage;
import com.sytecso.component.utility.MediaTypeUtils;
import com.sytecso.component.utility.UtileriaPDF;
import com.sytecso.dto.ResumenCtaVectorDTO;
import com.sytecso.dto.VectorDTO;

@Controller
@RequestMapping(path = "/PDF/")
public class ControllerReporte {
	
	 @Autowired
	 private ServletContext servletContext;
	@Autowired
	CustomContext context;
	
	@PostMapping(value = "/generatePDF")
	public ResponseEntity<EventMessage> generatePDF(@RequestBody ResumenCtaVectorDTO resumen) throws DocumentException, IOException {
		

		 String archivo=UtileriaPDF.generatePDF(resumen,context.getProperties());
		 //String name = archivo.substring(archivo.indexOf('R'),archivo.length());
			System.out.println(archivo);
		 return new ResponseEntity<>(new EventMessage(archivo), HttpStatus.OK);
			

	}
	
	
	 @RequestMapping(value="/download",	method = RequestMethod.GET)
	    public ResponseEntity<VectorDTO> downloadFile1( @RequestParam(name="filename") String fileName) throws IOException {
	 
	        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
	        System.out.println("fileName: " + fileName);
	        System.out.println("mediaType: " + mediaType);
	 
	        String ruta= System.getProperty("java.io.tmpdir");
			String tmpDirStr = ruta.endsWith("/temp")?ruta.concat("/"):ruta;
			
			System.out.println("ruta: " + ruta);
			System.out.println("tmpDirStr: " + tmpDirStr);
		  	  File file = new File(tmpDirStr+fileName);
		  	System.out.println("file: " + file);
	        
	        byte[] input_file = Files.readAllBytes(Paths.get(tmpDirStr+fileName));

	        byte[] encodedBytes = Base64.getEncoder().encode(input_file);
	        String encodedString =  new String(encodedBytes);
	        VectorDTO pdf = new VectorDTO();
	        pdf.setResponseMsg(encodedString);
	        System.out.println("encodedString: "+encodedString);
	     return new ResponseEntity<>(pdf, HttpStatus.OK);
	 
	    }
		  
		 

}
