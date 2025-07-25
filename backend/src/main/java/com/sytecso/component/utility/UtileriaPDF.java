package com.sytecso.component.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sytecso.dto.DetalleMovimientoVectorDTO;
import com.sytecso.dto.ResumenCtaVectorDTO;
import com.sytecso.dto.ResumenMovimientoVectorDTO;


public class UtileriaPDF {
	
	public static String generatePDF(ResumenCtaVectorDTO resumen, String properties) throws DocumentException, IOException {
		String DIR=System.getProperty("java.io.tmpdir");
		String FILE = DIR.endsWith("/temp")?DIR.concat("/"):DIR;
		String documento="Reporte_"+resumen.getCodigoRFC()+"-"+resumen.getPeriodoConsulta()+".pdf";
		 File folder = new File(FILE);
		 if (!folder.exists()) { 
			 folder.mkdirs();
		 }
		try { 
		
		Document document = new Document(PageSize.A4, 35, 30, 50, 60);
		String soRoute="";
		String soName="";
    	soName=System.getProperty("os.name").toLowerCase();
    	 if (soName.indexOf("win") >= 0) {
            soRoute="C:/properties/";
         }else {
        	 soRoute="/home/admin/properties/";
        	 //soRoute="/home/admin/propertiesDemoAP/";
         }
		String fileImage=soRoute+"aseguradora-patrimonial.jpg";
			
		Image imagen = Image.getInstance(fileImage);    
		
		
		PdfWriter.getInstance(document, new FileOutputStream(FILE+documento));
		
		document.open();
		float[] medidaCeldasInfo = {3.25f};
		
		PdfPTable tableDatosEncabezado = new PdfPTable(2);
		float[] medidaCeldasLogo = {1.30f,3.25f};
		tableDatosEncabezado.setWidths(medidaCeldasLogo);
		tableDatosEncabezado.setWidths(new float[] { 1, 3 });
		tableDatosEncabezado.setWidthPercentage(100);
		tableDatosEncabezado.getDefaultCell().setBorderColor(BaseColor.WHITE);
		addRowsEncabezado(tableDatosEncabezado,imagen);
        
		PdfPTable contenedorLogo = new PdfPTable(1);
		contenedorLogo.getDefaultCell().setBorderColor(BaseColor.WHITE);
        float[] medidaCeldasEncabezado = {3.25f};
        contenedorLogo.setWidths(medidaCeldasEncabezado);
        contenedorLogo.setWidthPercentage(100);
        addRowsLogo(contenedorLogo,tableDatosEncabezado);
        
        PdfPTable tableInfoServPub = new PdfPTable(1);
        tableInfoServPub.getDefaultCell().setBorderColor(BaseColor.WHITE);
        tableInfoServPub.setWidths(medidaCeldasInfo);
        tableInfoServPub.setWidthPercentage(100);
        addTableHeaderInfo(tableInfoServPub,"INFORMACIÓN DEL SERVIDOR PÚBLICO");
        
        PdfPTable infoServ = new PdfPTable(2);
        infoServ.getDefaultCell().setBorderColor(BaseColor.WHITE);
        infoServ.setWidthPercentage(100);
		addRowsinfoServ( infoServ,resumen);
		
		 PdfPTable tableResumMov = new PdfPTable(1);
		 tableResumMov.getDefaultCell().setBorderColor(BaseColor.WHITE);
		 tableResumMov.setWidths(medidaCeldasInfo);
		 tableResumMov.setWidthPercentage(100);
        addTableHeaderInfo(tableResumMov,"RESUMEN DE MOVIMIENTOS Y PERIODOS");
       
        PdfPTable resumMov = new PdfPTable(6);
        resumMov.getDefaultCell().setBorderColor(BaseColor.WHITE);
        float[] medidaCeldasResumMov = {1.25f, 0.55f, 0.55f, 0.55f,0.55f,0.55f};
        resumMov.setWidths(medidaCeldasResumMov);
        resumMov.setWidthPercentage(100);
        addTableHeaderResumMov(resumMov);
		addRowsResumMov( resumMov,resumen.getListResumenMovimiento());
		addRowsSpaceResumMov(resumMov);
		addRowTotalResumMov(resumMov,resumen.getListResumenMovimiento());
		
		PdfPTable tableObs = new PdfPTable(1);
		tableObs.getDefaultCell().setBorderColor(BaseColor.WHITE);
		tableObs.setWidths(medidaCeldasInfo);
		tableObs.setWidthPercentage(100);
		addTableHeaderInfo(tableObs,"OBSERVACIONES");
		
		PdfPTable tableObsTxt = new PdfPTable(1);
		tableObsTxt.getDefaultCell().setBorderColor(BaseColor.WHITE);
		tableObsTxt.setWidthPercentage(100);
       	addTableObsTxt(tableObsTxt);
		
		PdfPTable espacioBlack = new PdfPTable(1);
		espacioBlack.getDefaultCell().setBorderColor(BaseColor.WHITE);
		espacioBlack.setWidthPercentage(100);
		addRowsEspacioBlack(espacioBlack);
		
		PdfPTable espacioWhite = new PdfPTable(1);
		espacioWhite.getDefaultCell().setBorderColor(BaseColor.WHITE);
		espacioWhite.setWidthPercentage(100);
		addRowsEspacioWhite(espacioWhite);
		
		PdfPTable tableDetailMov = new PdfPTable(1);
		tableDetailMov.getDefaultCell().setBorderColor(BaseColor.WHITE);
		tableDetailMov.setWidths(medidaCeldasInfo);
		tableDetailMov.setWidthPercentage(100);
		addTableHeaderInfo(tableDetailMov,"DETALLE DE MOVIMIENTOS");
		
		PdfPTable detalleMov = new PdfPTable(6);
		detalleMov.getDefaultCell().setBorderColor(BaseColor.WHITE);
        float[] medidaCeldasdetalleMov = {0.55f,1.25f,0.55f, 0.55f,0.55f,0.55f};
		detalleMov.setWidths(medidaCeldasdetalleMov);
		detalleMov.setWidthPercentage(100);
        addTableHeaderDetalleMov(detalleMov);
		addRowsDetalleMov( detalleMov,resumen.getListDetalleMovimiento());
        
        document.add(contenedorLogo);
        document.add(tableInfoServPub);
        document.add(infoServ);
        document.add(tableResumMov);
        document.add(resumMov);
        document.add(espacioWhite);
        document.add(tableObs);
        document.add(tableObsTxt);
        document.add(espacioWhite);
        document.add(espacioBlack);
        document.add(espacioWhite);
        document.add(tableDetailMov);
        document.add(detalleMov);
		document.close();
	    
		} catch (Exception e) { 
		    e.printStackTrace(); 
		} 
		//return FILE+documento;
		return documento;
	}
	
	
	

private static void addRowsEncabezado(PdfPTable table, Image imagen) {
		
		PdfPCell cell = new PdfPCell(imagen);
		cell.setFixedHeight(60f);
		cell.setBorderColor(BaseColor.WHITE);
		table.addCell(cell);
		String txt="\r\nASEGURADORA PATRIMONIAL VIDA, S.A. DE C.V.\r\n" + 
				"\r\n" + 
				"ESTADO DE CUENTA SEGURO DE AHORRO";
		FontSelector selector1 = new FontSelector(); 
		Font f1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9); 
		
		f1.setColor(new BaseColor(37, 122, 169)); 
		selector1.addFont(f1); 
		Phrase ph = selector1.process(txt);//First one 
		
		table.addCell(ph);
		
	}


	private static void addRowsLogo(PdfPTable table, PdfPTable tableDatosEncabezado ) {
		   
	    table.addCell(tableDatosEncabezado);
	    table.addCell("");
	}
	
	
	private static void addTableHeaderInfo(PdfPTable table, String txt) {
		
		FontSelector selector1 = new FontSelector(); 
		Font f1 = FontFactory.getFont(FontFactory.HELVETICA, 7); 
		
		f1.setColor(BaseColor.WHITE); 
		selector1.addFont(f1); 
		Phrase ph = selector1.process(txt);//First one 
	    Stream.of(ph)
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(new BaseColor(37, 122, 169));			  
	        header.setBorderColor(BaseColor.WHITE);
	        header.setFixedHeight(15f); 
	        header.setVerticalAlignment(Element.ALIGN_CENTER );
	        header.setHorizontalAlignment(Element.ALIGN_CENTER );
	        header.setPhrase(columnTitle);
	        table.addCell(header);
	    });
	}
	
private static void addRowsinfoServ(PdfPTable table, ResumenCtaVectorDTO r) {

    
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); 
    
		
		table.addCell(new Phrase("\r\nR.F.C.: "+r.getCodigoRFC()+"\r\n\r\n"+
					  "Retenedor: "+r.getRetenedor()+"\r\n\r\n"+
					  "Dependencia: "+r.getDependencia()+"\r\n\r\n",FontFactory.getFont(FontFactory.HELVETICA, 7)));
		table.addCell(new Phrase("\r\nPeriodo: "+r.getPeriodoConsulta()+"\r\n\r\n"+
					  "Poliza: "+r.getNumPoliza()+"\r\n\r\n",FontFactory.getFont(FontFactory.HELVETICA, 7)));
		
	}
	
	private static void addTableHeaderResumMov(PdfPTable table) {
		int pass=0;
		Stream.of("\r\nDESCRIPCIÓN\r\n","\r\nSALDO INICIAL\r\n","\r\nPRIMAS APORTADAS\r\n","\r\nINTERESES GANADOS\r\n","\r\nRETIROS\r\n","\r\nSALDO FINAL\r\n")
	      .forEach(columnTitle -> {
		        PdfPCell header = new PdfPCell();
		        header.setBorderColor(BaseColor.WHITE);
		        header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.HELVETICA, 7,Font.BOLD)));
		        if(columnTitle.contains("DESCRIP"))
		        	header.setHorizontalAlignment(Element.ALIGN_LEFT);
		        else
		        	header.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        table.addCell(header);
		    });
		}
	
	private static void addRowsResumMov(PdfPTable table, List<ResumenMovimientoVectorDTO> list) {
		 DecimalFormat formateador = new DecimalFormat("###,###,###,###.##");
		for (int i = 0; i<list.size(); i++){ 
			PdfPCell cellDesc = new PdfPCell(new Phrase(list.get(i).getDescripcion(), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellDesc.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDesc.setBorderColor(BaseColor.WHITE);
			table.addCell(cellDesc); 
			PdfPCell cellSaldoI = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getSaldoInicial()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellSaldoI.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSaldoI.setBorderColor(BaseColor.WHITE);
			table.addCell(cellSaldoI); 
			PdfPCell cellPrims = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getPrimasAportadas()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellPrims.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellPrims.setBorderColor(BaseColor.WHITE);
			table.addCell(cellPrims); 
			PdfPCell cellIntG = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getInteresGanado()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellIntG.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellIntG.setBorderColor(BaseColor.WHITE);
			table.addCell(cellIntG); 
			PdfPCell cellRetiros = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getRetiros()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellRetiros.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellRetiros.setBorderColor(BaseColor.WHITE);
			table.addCell(cellRetiros); 
			PdfPCell cellSaldoF = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getSaldoFinal()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellSaldoF.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSaldoF.setBorderColor(BaseColor.WHITE);
			table.addCell(cellSaldoF); 
		} 		
	}
	
	private static void addRowsSpaceResumMov(PdfPTable table) {
		table.addCell("\r\n");
		table.addCell("\r\n");
		table.addCell("\r\n");
		table.addCell("\r\n");
		table.addCell("\r\n");
		table.addCell("\r\n");
	}
	
	
	private static void addRowTotalResumMov(PdfPTable table,	List<ResumenMovimientoVectorDTO> list) {
		DecimalFormat formateador = new DecimalFormat("###,###,###,###.##");
		 BigDecimal saldoIni = new BigDecimal(0);
		 BigDecimal primasApo = new BigDecimal(0);
		 BigDecimal interGan = new BigDecimal(0);
		 BigDecimal retiros = new BigDecimal(0);
		 BigDecimal saldoFin = new BigDecimal(0);
		for (int i = 0; i<list.size(); i++){ 
			saldoIni=list.get(i).getSaldoInicial().add(saldoIni);
			primasApo=list.get(i).getPrimasAportadas().add(primasApo);
			interGan=list.get(i).getInteresGanado().add(interGan);
			retiros=list.get(i).getRetiros().add(retiros);
			saldoFin=list.get(i).getSaldoFinal().add(saldoFin);
			}
			PdfPCell cellTotal= new PdfPCell(new Phrase("Total", FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellTotal.setBorderColor(BaseColor.WHITE);
			table.addCell(cellTotal); 
			PdfPCell cellSaldoI = new PdfPCell(new Phrase("$"+formateador.format(saldoIni), FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellSaldoI.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSaldoI.setBorderColor(BaseColor.WHITE);
			table.addCell(cellSaldoI); 
			PdfPCell cellPrims = new PdfPCell(new Phrase("$"+formateador.format(primasApo), FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellPrims.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellPrims.setBorderColor(BaseColor.WHITE);
			table.addCell(cellPrims); 
			PdfPCell cellIntG = new PdfPCell(new Phrase("$"+formateador.format(interGan), FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellIntG.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellIntG.setBorderColor(BaseColor.WHITE);
			table.addCell(cellIntG); 
			PdfPCell cellRetiros = new PdfPCell(new Phrase("$"+formateador.format(retiros), FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellRetiros.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellRetiros.setBorderColor(BaseColor.WHITE);
			table.addCell(cellRetiros); 
			PdfPCell cellSaldoF = new PdfPCell(new Phrase("$"+formateador.format(saldoFin), FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellSaldoF.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSaldoF.setBorderColor(BaseColor.WHITE);
			table.addCell(cellSaldoF); 
		
	}
	
	private static void addTableObsTxt(PdfPTable table) {
		table.addCell(new Phrase("\r\nVerifique que sus datos personales estén correctos, en caso de existir algún error u omisión acuda al área de Recursos Humanos "
				+ "de su dependencia, unidad o equivalente de su centro de trabajo, para que se requisite el formato para la actualización de los mismos.\r\n",
				FontFactory.getFont(FontFactory.HELVETICA, 6)));
		
	}
	
	private static void addRowsEspacioBlack(PdfPTable table) {
		PdfPCell cellSpace = new PdfPCell();
		cellSpace.setBackgroundColor(BaseColor.BLACK);
		cellSpace.setFixedHeight(3f); 
	    table.addCell(cellSpace);
	}
	private static void addRowsEspacioWhite(PdfPTable table) {
		PdfPCell cellSpace = new PdfPCell();
		cellSpace.setBorderColor(BaseColor.WHITE);
		cellSpace.setBackgroundColor(BaseColor.WHITE);
		cellSpace.setFixedHeight(7f); 
	    table.addCell(cellSpace);
	}
	
	private static void addTableHeaderDetalleMov(PdfPTable table) {
		Stream.of("\r\nFECHA\r\n","\r\nCONCEPTO\r\n","\r\nDEPÓSITO\r\n","\r\nINTERESES\r\n","\r\nRETIROS\r\n","\r\nSALDO\r\n")
	      .forEach(columnTitle -> {
		        PdfPCell header = new PdfPCell();
		        header.setBorderColor(BaseColor.WHITE);
		        header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.HELVETICA, 7,Font.BOLD)));
		        header.setHorizontalAlignment(Element.ALIGN_LEFT);
		        table.addCell(header);
		    });
		}
	
	private static void addRowsDetalleMov(PdfPTable table,	List<DetalleMovimientoVectorDTO> list) throws ParseException {
		 DecimalFormat formateador = new DecimalFormat("###,###,###,###.##");
		 SimpleDateFormat parseador = new SimpleDateFormat("yyyyMMdd");
		 SimpleDateFormat formateadoFec = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i<list.size(); i++){ 
			//Date date = parseador.parse(list.get(i).getFechaMov());
			PdfPCell cellFecha = new PdfPCell(new Phrase(list.get(i).getFechaMov(), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellFecha.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellFecha.setBorderColor(BaseColor.WHITE);
			table.addCell(cellFecha); 
			PdfPCell cellConcept= new PdfPCell(new Phrase(list.get(i).getConcepto(), FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			cellConcept.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellConcept.setBorderColor(BaseColor.WHITE);
			table.addCell(cellConcept); 
			PdfPCell cellDeposito = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getImpDeposito()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellDeposito .setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDeposito.setBorderColor(BaseColor.WHITE);
			table.addCell(cellDeposito);
			PdfPCell cellInteres = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getImpIntereses()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellInteres .setHorizontalAlignment(Element.ALIGN_LEFT);
			cellInteres.setBorderColor(BaseColor.WHITE);
			table.addCell(cellInteres);
			PdfPCell cellRetencion= new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getImpRetencion()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellRetencion .setHorizontalAlignment(Element.ALIGN_LEFT);
			cellRetencion.setBorderColor(BaseColor.WHITE);
			table.addCell(cellRetencion);
			PdfPCell cellSaldo = new PdfPCell(new Phrase("$"+formateador.format(list.get(i).getImpSaldo()), FontFactory.getFont(FontFactory.HELVETICA, 6)));
			cellSaldo .setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSaldo.setBorderColor(BaseColor.WHITE);
			table.addCell(cellSaldo);
		}
		
	}

	
	public static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    }
	    byte[] bytes = new byte[(int)length];
	    
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }

	    is.close();
	    return bytes;
	}
	
}
