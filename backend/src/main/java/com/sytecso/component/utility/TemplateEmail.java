package com.sytecso.component.utility;


public class TemplateEmail {
	
	public static String template(String nombre, String url) {
		String html="<table id=\"mainStructure\" style=\"background-color: #ffffff;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><!-- START LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) -->" + 
				"  <tbody>" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-image: url('https://www.cloudHQ.net/system/content/templates/images/set4-header-bg3.jpg'); background-color: #3f5670; background-size: cover !important; background-position: 50% 100% !important; background-repeat: no-repeat !important;\" align=\"center\" valign=\"top\">" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 155px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 105px;\">" + 
				"                      <td style=\"height: 105px; width: 560px;\" valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td valign=\"top\">" + 
				"                                <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\" height=\"30\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"<!-- end space --> <!-- start content / button -->" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\">" + 
				"                                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start content -->" + 
				"                                          <tbody>" + 
				"                                            <tr>" + 
				"                                              <td style=\"padding-left: 20px; padding-right: 20px;\" valign=\"top\">" + 
				"                                                <table style=\"height: 75px; width: 100%;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                                                  <tbody>" + 
				"                                                    <tr style=\"height: 34px;\">" + 
				"                                                      <td style=\"font-size: 30px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 34px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"color: #ffffff; font-size: 30px; line-height: 30px;\">Restablecer Contraseña</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"<!-- start space -->" + 
				"                                                    <tr style=\"height: 17px;\">" + 
				"                                                      <td style=\"height: 17px; width: 520px;\" valign=\"top\" height=\"10\">&nbsp;</td>" + 
				"                                                    </tr>" + 
				"<!-- end space -->" + 
				"                                                    <tr style=\"height: 24px;\">" + 
				"                                                      <td style=\"font-size: 14px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 24px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"                                                  </tbody>" + 
				"                                                </table>" + 
				"                                              </td>" + 
				"                                            </tr>" + 
				"<!-- end content -->" + 
				"                                          </tbody>" + 
				"                                        </table>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end content / button -->" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- start space -->" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 560px;\" valign=\"top\" height=\"50\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) --> <!--START LAYOUT-13 ( 2-COL TEXT / BG )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #f7f7f7;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"background-color: #f7f7f7; min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 471px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 1026px;\" valign=\"top\" height=\"50\">&nbsp; " + 
				"                        <img src=\"https://650b5f923e416651dfc9cd6a--merry-custard-87eb74.netlify.app/assets/ap/LogoTransparente.png\" alt=\"\" width=\"500\" height=\"240\">" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr style=\"height: 381px;\">" + 
				"                      <td style=\"height: 381px; width: 1026px;\" valign=\"top\">" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Estimado</span> " + 
				"                          <strong>"+nombre+"</strong> " + 
				"                          <span style=\"font-weight: 400;\">.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Se solicitó recientemente cambiar la contraseña de su cuenta.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Si usted solicitó este cambio de contraseña, pulse el enlace siguiente para establecer una nueva contraseña dentro de 24 horas:</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <strong>" + 
				"                            <a href=\" "+url+"\" style=\"background-color: #216085; color: #ffffff; border: 0px solid #000000; border-radius: 3px; box-sizing: border-box; font-size: 13px; "
				+ "							font-weight: bold; line-height: 40px; padding: 12px 24px; text-align: center; text-decoration: none; text-transform: uppercase; vertical-align: middle;\" rel=\"noopener\">CAMBIO CONTRASEÑA!</a>" + 
				"                          </strong> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Si el boton anterior no funciona, copie lo siguiente en su navegador:</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span>" + 
				"                          <span style=\"font-weight: 400;\"> "+url+ 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Si no desea cambiar su contraseña, ignore este mensaje.</span> "+
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Mensaje automático por favor no responda este mensaje</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Gracias.</span>" + 
				"                        </p>" + 
				"                        <br>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr style=\"height: 40px;\">" + 
				"                      <td style=\"height: 40px; width: 1026px;\" valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!--END LAYOUT-13 ( 2-COL TEXT / BG ) --> <!--START LAYOUT-15 ( CONTACT US / ABOUT US )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #2c2c31;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; background-color: #2c2c31; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; border-color: #0f0e0e; width: 560px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td align=\"center\" valign=\"top\">" + 
				"                                <table class=\"col-2\" dir=\"ltr\" style=\"height: 122px;\" border=\"0\" width=\"397\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 20px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; width: 397px;\" align=\"left\">" + 
				"                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end space -->" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 14px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; padding-right: 10px; word-break: break-word; width: 387px;\" align=\"left\">" + 
				"                                        <span style=\"text-decoration: none; color: #ffffff; font-size: inherit; line-height: 24px;\">" + 
				"                                          <span style=\"font-weight: 400;\">Visite nuestra Página: www.spsegurospatrimonial.mx</span>." + 
				"                                        </span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                                <table class=\"space-w-25\" dir=\"ltr\" style=\"min-width: 25px; height: 1px; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0;\" border=\"0\" width=\"25\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td class=\"h-40\" style=\"display: block; font-size: 0px; line-height: 0; border-collapse: collapse;\" width=\"25\" height=\"1\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-14 ( CONTACT US / ABOUT US ) -->" + 
				"  </tbody>" + 
				"</table>";
		
		return html;
	}

	public static String templateCambioContraseñaExito(String nombre, String fecha) {
		String html="<table id=\"mainStructure\" style=\"background-color: #ffffff;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><!-- START LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) -->" + 
				"  <tbody>" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-image: url('https://www.cloudHQ.net/system/content/templates/images/set4-header-bg3.jpg'); background-color: #3f5670; background-size: cover !important; background-position: 50% 100% !important; background-repeat: no-repeat !important;\" align=\"center\" valign=\"top\">" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 155px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 105px;\">" + 
				"                      <td style=\"height: 105px; width: 560px;\" valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td valign=\"top\">" + 
				"                                <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\" height=\"30\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"<!-- end space --> <!-- start content / button -->" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\">" + 
				"                                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start content -->" + 
				"                                          <tbody>" + 
				"                                            <tr>" + 
				"                                              <td style=\"padding-left: 20px; padding-right: 20px;\" valign=\"top\">" + 
				"                                                <table style=\"height: 75px; width: 100%;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                                                  <tbody>" + 
				"                                                    <tr style=\"height: 34px;\">" + 
				"                                                      <td style=\"font-size: 30px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 34px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"color: #ffffff; font-size: 30px; line-height: 30px;\">Restablecer Contraseña</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"<!-- start space -->" + 
				"                                                    <tr style=\"height: 17px;\">" + 
				"                                                      <td style=\"height: 17px; width: 520px;\" valign=\"top\" height=\"10\">&nbsp;</td>" + 
				"                                                    </tr>" + 
				"<!-- end space -->" + 
				"                                                    <tr style=\"height: 24px;\">" + 
				"                                                      <td style=\"font-size: 14px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 24px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"                                                  </tbody>" + 
				"                                                </table>" + 
				"                                              </td>" + 
				"                                            </tr>" + 
				"<!-- end content -->" + 
				"                                          </tbody>" + 
				"                                        </table>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end content / button -->" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- start space -->" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 560px;\" valign=\"top\" height=\"50\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) --> <!--START LAYOUT-13 ( 2-COL TEXT / BG )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #f7f7f7;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"background-color: #f7f7f7; min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 471px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 1026px;\" valign=\"top\" height=\"50\">&nbsp; " + 
				"                        <img src=\"https://650b5f923e416651dfc9cd6a--merry-custard-87eb74.netlify.app/assets/ap/LogoTransparente.png\" alt=\"\" width=\"500\" height=\"240\">" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr style=\"height: 381px;\">" + 
				"                      <td style=\"height: 381px; width: 1026px;\" valign=\"top\">" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Estimado</span> " + 
				"                          <strong>"+nombre+"</strong> " + 
				"                          <span style=\"font-weight: 400;\">.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>La contraseña de su cuenta" + 
				"                          </span> " + 
				"                          <strong>www.spsegurospatrimonial.mx</strong> " + 
				"                          <span style=\"font-weight: 400;\">se restableció con éxito el</span> " + 
				"                          <strong>"+fecha+"</strong>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Mensaje automático por favor no responda este mensaje</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Gracias.</span>" + 
				"                        </p>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr style=\"height: 40px;\">" + 
				"                      <td style=\"height: 40px; width: 1026px;\" valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!--END LAYOUT-13 ( 2-COL TEXT / BG ) --> <!--START LAYOUT-15 ( CONTACT US / ABOUT US )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #2c2c31;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; background-color: #2c2c31; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; border-color: #0f0e0e; width: 560px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td align=\"center\" valign=\"top\">" + 
				"                                <table class=\"col-2\" dir=\"ltr\" style=\"height: 122px;\" border=\"0\" width=\"397\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 20px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; width: 397px;\" align=\"left\">" + 
				"                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end space -->" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 14px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; padding-right: 10px; word-break: break-word; width: 387px;\" align=\"left\">" + 
				"                                        <span style=\"text-decoration: none; color: #ffffff; font-size: inherit; line-height: 24px;\">" + 
				"                                          <span style=\"font-weight: 400;\">Visite nuestra Página: www.spsegurospatrimonial.mx</span>." + 
				"                                        </span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                                <table class=\"space-w-25\" dir=\"ltr\" style=\"min-width: 25px; height: 1px; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0;\" border=\"0\" width=\"25\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td class=\"h-40\" style=\"display: block; font-size: 0px; line-height: 0; border-collapse: collapse;\" width=\"25\" height=\"1\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-14 ( CONTACT US / ABOUT US ) -->" + 
				"  </tbody>" + 
				"</table>";
		return html;
		
	}
	
	public static String templateTest() {
		String html="<html>   " + 
				"				<head>  " + 
				"				</head>   " + 
				"				<body>   " + 
				"			  " + 
				"				<table  border = \"3\" bordercolor = \"white\"  >   " + 
				"				         <tr bgcolor = #AAAAAA>   " + 
				"				            <th colspan =  4 >Logo</th>   " + 
				"				         </tr>   " + 
				"				         <tr bgcolor = #257aa9>   " + 
				"				            <td colspan =  4> " + 
				"                            <p style=\"color: #FFFFFF; font-size: 12pt\">INFORMACIÓN DEL SERVIDOR PÚBLICO</p> " + 
				"                            </td>   " + 
				"				         </tr> " + 
				"                         <tr>   " + 
				"				            <td><strong>R.F.C.:</strong></td>   " + 
				"                            <td><strong>Periodo:</strong></td>  " + 
				"				         </tr> " + 
				"				         <tr>   " + 
				"				            <td><strong>Retenedor:</strong></td>   " + 
				"                            <td><strong>Poliza:</strong></td> " + 
				"				         </tr>   " + 
				"				         <tr>   " + 
				"				           <td><strong>Dependencia:</strong></td>   " + 
				"				         </tr>   " + 
				"                         <tr bgcolor = #257aa9>   " + 
				"				            <td colspan =  4> " + 
				"                            <p style=\"color: #FFFFFF; font-size: 12pt\">OBSERVACIONES</p> " + 
				"                            </td>   " + 
				"				         </tr> " + 
				"                         <tr>   " + 
				"				            <td>Verifique que sus datos personales estén correctos, en caso de existir algún error u omisión acuda al área de Recursos Humanos de su dependencia, unidad o equivalente de su centro de trabajo, para que se requisite el formato para la actualización de los mismos.</td>   " + 
				"				         </tr> " + 
				"				      </table>   " + 
				"				</body>   " + 
				"				</html>";
		return html;
		
	}

	public static String templateUpdateDatos(String nombreCompleo) {
		String html="<table id=\"mainStructure\" style=\"background-color: #ffffff;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><!-- START LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) -->" + 
				"  <tbody>" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-image: url('https://www.cloudHQ.net/system/content/templates/images/set4-header-bg3.jpg'); background-color: #3f5670; background-size: cover !important; background-position: 50% 100% !important; background-repeat: no-repeat !important;\" align=\"center\" valign=\"top\">" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 155px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 105px;\">" + 
				"                      <td style=\"height: 105px; width: 560px;\" valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td valign=\"top\">" + 
				"                                <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\" height=\"30\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"<!-- end space --> <!-- start content / button -->" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\">" + 
				"                                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start content -->" + 
				"                                          <tbody>" + 
				"                                            <tr>" + 
				"                                              <td style=\"padding-left: 20px; padding-right: 20px;\" valign=\"top\">" + 
				"                                                <table style=\"height: 75px; width: 100%;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                                                  <tbody>" + 
				"                                                    <tr style=\"height: 34px;\">" + 
				"                                                      <td style=\"font-size: 30px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 34px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"color: #ffffff; font-size: 30px; line-height: 30px;\">Actualización de Datos</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"<!-- start space -->" + 
				"                                                    <tr style=\"height: 17px;\">" + 
				"                                                      <td style=\"height: 17px; width: 520px;\" valign=\"top\" height=\"10\">&nbsp;</td>" + 
				"                                                    </tr>" + 
				"<!-- end space -->" + 
				"                                                    <tr style=\"height: 24px;\">" + 
				"                                                      <td style=\"font-size: 14px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 24px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"                                                  </tbody>" + 
				"                                                </table>" + 
				"                                              </td>" + 
				"                                            </tr>" + 
				"<!-- end content -->" + 
				"                                          </tbody>" + 
				"                                        </table>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end content / button -->" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- start space -->" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 560px;\" valign=\"top\" height=\"50\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) --> <!--START LAYOUT-13 ( 2-COL TEXT / BG )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #f7f7f7;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"background-color: #f7f7f7; min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 471px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 1026px;\" valign=\"top\" height=\"50\">&nbsp; " + 
				"                        <img src=\"https://650b5f923e416651dfc9cd6a--merry-custard-87eb74.netlify.app/assets/ap/LogoTransparente.png\" alt=\"\" width=\"500\" height=\"240\">" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr style=\"height: 381px;\">" + 
				"                      <td style=\"height: 381px; width: 1026px;\" valign=\"top\">" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Estimado</span> " + 
				"                          <strong>"+nombreCompleo+"</strong> " + 
				"                          <span style=\"font-weight: 400;\">.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">La petición para actualizar los datos de acceso al portal www.spsegurospatrimonial.mx ha sido exitosa</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Le recordamos que a través del portal usted puede consultar los estados de cuenta mes.</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Mensaje automático por favor no responda este mensaje</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Gracias.</span>" + 
				"                        </p>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr style=\"height: 40px;\">" + 
				"                      <td style=\"height: 40px; width: 1026px;\" valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!--END LAYOUT-13 ( 2-COL TEXT / BG ) --> <!--START LAYOUT-15 ( CONTACT US / ABOUT US )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #2c2c31;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; background-color: #2c2c31; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; border-color: #0f0e0e; width: 560px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td align=\"center\" valign=\"top\">" + 
				"                                <table class=\"col-2\" dir=\"ltr\" style=\"height: 122px;\" border=\"0\" width=\"397\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 20px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; width: 397px;\" align=\"left\">" + 
				"                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end space -->" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 14px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; padding-right: 10px; word-break: break-word; width: 387px;\" align=\"left\">" + 
				"                                        <span style=\"text-decoration: none; color: #ffffff; font-size: inherit; line-height: 24px;\">" + 
				"                                          <span style=\"font-weight: 400;\">Visite nuestra Página: www.spsegurospatrimonial.mx</span>." + 
				"                                        </span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                                <table class=\"space-w-25\" dir=\"ltr\" style=\"min-width: 25px; height: 1px; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0;\" border=\"0\" width=\"25\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td class=\"h-40\" style=\"display: block; font-size: 0px; line-height: 0; border-collapse: collapse;\" width=\"25\" height=\"1\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-14 ( CONTACT US / ABOUT US ) -->" + 
				"  </tbody>" + 
				"</table>" + 
				"";
		return html;
	}

	public static Object templateCambioPendienteDocs(String nombre, String fecha) {
		String html="<table id=\"mainStructure\" style=\"background-color: #ffffff;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><!-- START LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) -->" + 
				"  <tbody>" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-image: url('https://www.cloudHQ.net/system/content/templates/images/set4-header-bg3.jpg'); background-color: #3f5670; background-size: cover !important; background-position: 50% 100% !important; background-repeat: no-repeat !important;\" align=\"center\" valign=\"top\">" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 155px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 105px;\">" + 
				"                      <td style=\"height: 105px; width: 560px;\" valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td valign=\"top\">" + 
				"                                <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\" height=\"30\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"<!-- end space --> <!-- start content / button -->" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\">" + 
				"                                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start content -->" + 
				"                                          <tbody>" + 
				"                                            <tr>" + 
				"                                              <td style=\"padding-left: 20px; padding-right: 20px;\" valign=\"top\">" + 
				"                                                <table style=\"height: 75px; width: 100%;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                                                  <tbody>" + 
				"                                                    <tr style=\"height: 34px;\">" + 
				"                                                      <td style=\"font-size: 30px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 34px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"color: #ffffff; font-size: 30px; line-height: 30px;\">Documentos Pendientes</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"<!-- start space -->" + 
				"                                                    <tr style=\"height: 17px;\">" + 
				"                                                      <td style=\"height: 17px; width: 520px;\" valign=\"top\" height=\"10\">&nbsp;</td>" + 
				"                                                    </tr>" + 
				"<!-- end space -->" + 
				"                                                    <tr style=\"height: 24px;\">" + 
				"                                                      <td style=\"font-size: 14px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 24px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"                                                  </tbody>" + 
				"                                                </table>" + 
				"                                              </td>" + 
				"                                            </tr>" + 
				"<!-- end content -->" + 
				"                                          </tbody>" + 
				"                                        </table>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end content / button -->" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- start space -->" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 560px;\" valign=\"top\" height=\"50\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) --> <!--START LAYOUT-13 ( 2-COL TEXT / BG )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #f7f7f7;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"background-color: #f7f7f7; min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 471px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 1026px;\" valign=\"top\" height=\"50\">&nbsp; " + 
				"                        <img src=\"https://650b5f923e416651dfc9cd6a--merry-custard-87eb74.netlify.app/assets/ap/LogoTransparente.png\" alt=\"\" width=\"500\" height=\"240\">" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr style=\"height: 381px;\">" + 
				"                      <td style=\"height: 381px; width: 1026px;\" valign=\"top\">" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Estimado</span> " + 
				"                          <strong> "+nombre+" </strong> " + 
				"                          <span style=\"font-weight: 400;\">.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Se solicit&oacute; recientemente m&aacute;s documentos en su cuenta.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> . de C.V.</span>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Si no realiz&oacute; ninguna solicitud, ignore este mensaje.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Mensaje autom&aacute;tico por favor no responda este mensaje</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Gracias.</span>" + 
				"                        </p>" + 
				"                        <br>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr style=\"height: 40px;\">" + 
				"                      <td style=\"height: 40px; width: 1026px;\" valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!--END LAYOUT-13 ( 2-COL TEXT / BG ) --> <!--START LAYOUT-15 ( CONTACT US / ABOUT US )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #2c2c31;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; background-color: #2c2c31; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; border-color: #0f0e0e; width: 560px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td align=\"center\" valign=\"top\">" + 
				"                                <table class=\"col-2\" dir=\"ltr\" style=\"height: 122px;\" border=\"0\" width=\"397\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 20px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; width: 397px;\" align=\"left\">" + 
				"                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end space -->" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 14px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; padding-right: 10px; word-break: break-word; width: 387px;\" align=\"left\">" + 
				"                                        <span style=\"text-decoration: none; color: #ffffff; font-size: inherit; line-height: 24px;\">" + 
				"                                          <span style=\"font-weight: 400;\">Visite nuestra Página: www.spsegurospatrimonial.mx</span>." + 
				"                                        </span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                                <table class=\"space-w-25\" dir=\"ltr\" style=\"min-width: 25px; height: 1px; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0;\" border=\"0\" width=\"25\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td class=\"h-40\" style=\"display: block; font-size: 0px; line-height: 0; border-collapse: collapse;\" width=\"25\" height=\"1\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-14 ( CONTACT US / ABOUT US ) -->" + 
				"  </tbody>" + 
				"</table>" + 
				"";
		return html;
	}

	
	public static Object templateCreacionSolicitudes(String nombre, String fecha, String tipoTramite, String numeroRegistro) {
		String html="<table id=\"mainStructure\" style=\"background-color: #ffffff;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><!-- START LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) -->" + 
				"  <tbody>" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-image: url('https://www.cloudHQ.net/system/content/templates/images/set4-header-bg3.jpg'); background-color: #3f5670; background-size: cover !important; background-position: 50% 100% !important; background-repeat: no-repeat !important;\" align=\"center\" valign=\"top\">" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 155px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 105px;\">" + 
				"                      <td style=\"height: 105px; width: 560px;\" valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td valign=\"top\">" + 
				"                                <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\" height=\"30\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"<!-- end space --> <!-- start content / button -->" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\">" + 
				"                                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start content -->" + 
				"                                          <tbody>" + 
				"                                            <tr>" + 
				"                                              <td style=\"padding-left: 20px; padding-right: 20px;\" valign=\"top\">" + 
				"                                                <table style=\"height: 75px; width: 100%;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                                                  <tbody>" + 
				"                                                    <tr style=\"height: 34px;\">" + 
				"                                                      <td style=\"font-size: 30px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 34px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"color: #ffffff; font-size: 30px; line-height: 30px;\">Solicitud de retiro</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"<!-- start space -->" + 
				"                                                    <tr style=\"height: 17px;\">" + 
				"                                                      <td style=\"height: 17px; width: 520px;\" valign=\"top\" height=\"10\">&nbsp;</td>" + 
				"                                                    </tr>" + 
				"<!-- end space -->" + 
				"                                                    <tr style=\"height: 24px;\">" + 
				"                                                      <td style=\"font-size: 14px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 24px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"                                                  </tbody>" + 
				"                                                </table>" + 
				"                                              </td>" + 
				"                                            </tr>" + 
				"<!-- end content -->" + 
				"                                          </tbody>" + 
				"                                        </table>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end content / button -->" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- start space -->" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 560px;\" valign=\"top\" height=\"50\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) --> <!--START LAYOUT-13 ( 2-COL TEXT / BG )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #f7f7f7;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"background-color: #f7f7f7; min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 471px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 1026px;\" valign=\"top\" height=\"50\">&nbsp; " + 
				"                        <img src=\"https://650b5f923e416651dfc9cd6a--merry-custard-87eb74.netlify.app/assets/ap/LogoTransparente.png\" alt=\"\" width=\"500\" height=\"240\">" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr style=\"height: 381px;\">" + 
				"                      <td style=\"height: 381px; width: 1026px;\" valign=\"top\">" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400; color: #000\">Estimado Asegurado </span> " + 
				"                          <strong> "+nombre+" </strong> " + 
				"                          <span style=\"font-weight: 400;\">.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400; color: #000\">Confirmamos recepci&oacute;n de documentos, realizaremos la revisi&oacute;n para dar respuesta a la brevedad posible. " + 
				"							 <br>" + 
				"							 <br>" + 
				"						   El folio de seguimiento ser&aacute; <strong>" +numeroRegistro + "</strong> </span> " + 
				"                            <br>" + 
				"                          <span style=\"font-weight: 400;\"></span>" +
				"                            <br>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Si no realiz&oacute; ninguna solicitud, ignore este mensaje.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Mensaje autom&aacute;tico por favor no responda este mensaje</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Gracias.</span>" + 
				"                        </p>" + 
				"                        <br>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr style=\"height: 40px;\">" + 
				"                      <td style=\"height: 40px; width: 1026px;\" valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!--END LAYOUT-13 ( 2-COL TEXT / BG ) --> <!--START LAYOUT-15 ( CONTACT US / ABOUT US )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #2c2c31;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; background-color: #2c2c31; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; border-color: #0f0e0e; width: 560px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td align=\"center\" valign=\"top\">" + 
				"                                <table class=\"col-2\" dir=\"ltr\" style=\"height: 122px;\" border=\"0\" width=\"397\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 20px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; width: 397px;\" align=\"left\">" + 
				"                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end space -->" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 14px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; padding-right: 10px; word-break: break-word; width: 387px;\" align=\"left\">" + 
				"                                        <span style=\"text-decoration: none; color: #ffffff; font-size: inherit; line-height: 24px;\">" + 
				"                                          <span style=\"font-weight: 400;\">Visite nuestra P&aacute;gina: www.spsegurospatrimonial.mx</span>." + 
				"                                        </span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                                <table class=\"space-w-25\" dir=\"ltr\" style=\"min-width: 25px; height: 1px; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0;\" border=\"0\" width=\"25\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td class=\"h-40\" style=\"display: block; font-size: 0px; line-height: 0; border-collapse: collapse;\" width=\"25\" height=\"1\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-14 ( CONTACT US / ABOUT US ) -->" + 
				"  </tbody>" + 
				"</table>" + 
				"";
		return html;
	}
	
	public static Object templateCreacionAclaraciones(String nombre, String fecha, String tipoTramite, String numeroRegistro) {
		String html="<table id=\"mainStructure\" style=\"background-color: #ffffff;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><!-- START LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) -->" + 
				"  <tbody>" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-image: url('https://www.cloudHQ.net/system/content/templates/images/set4-header-bg3.jpg'); background-color: #3f5670; background-size: cover !important; background-position: 50% 100% !important; background-repeat: no-repeat !important;\" align=\"center\" valign=\"top\">" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 155px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 105px;\">" + 
				"                      <td style=\"height: 105px; width: 560px;\" valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td valign=\"top\">" + 
				"                                <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\" height=\"30\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"<!-- end space --> <!-- start content / button -->" + 
				"                                    <tr>" + 
				"                                      <td valign=\"top\">" + 
				"                                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start content -->" + 
				"                                          <tbody>" + 
				"                                            <tr>" + 
				"                                              <td style=\"padding-left: 20px; padding-right: 20px;\" valign=\"top\">" + 
				"                                                <table style=\"height: 75px; width: 100%;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                                                  <tbody>" + 
				"                                                    <tr style=\"height: 34px;\">" + 
				"                                                      <td style=\"font-size: 30px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 34px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"color: #ffffff; font-size: 30px; line-height: 30px;\">Creación de Aclaración</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"<!-- start space -->" + 
				"                                                    <tr style=\"height: 17px;\">" + 
				"                                                      <td style=\"height: 17px; width: 520px;\" valign=\"top\" height=\"10\">&nbsp;</td>" + 
				"                                                    </tr>" + 
				"<!-- end space -->" + 
				"                                                    <tr style=\"height: 24px;\">" + 
				"                                                      <td style=\"font-size: 14px; color: #ffffff; font-weight: normal; text-align: center; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; height: 24px; width: 520px;\" align=\"center\">" + 
				"                                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                                      </td>" + 
				"                                                    </tr>" + 
				"                                                  </tbody>" + 
				"                                                </table>" + 
				"                                              </td>" + 
				"                                            </tr>" + 
				"<!-- end content -->" + 
				"                                          </tbody>" + 
				"                                        </table>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end content / button -->" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- start space -->" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 560px;\" valign=\"top\" height=\"50\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-12 ( TITLE TEXT CENTER / BUTTON ) --> <!--START LAYOUT-13 ( 2-COL TEXT / BG )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #f7f7f7;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"background-color: #f7f7f7; min-width: 600px; margin: 0 auto; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; height: 471px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr style=\"height: 50px;\">" + 
				"                      <td style=\"height: 50px; width: 1026px;\" valign=\"top\" height=\"50\">&nbsp; " + 
				"                        <img src=\"https://650b5f923e416651dfc9cd6a--merry-custard-87eb74.netlify.app/assets/ap/LogoTransparente.png\" alt=\"\" width=\"500\" height=\"240\">" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr style=\"height: 381px;\">" + 
				"                      <td style=\"height: 381px; width: 1026px;\" valign=\"top\">" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400; color: #000\">Estimado Asegurado </span> " + 
				"                          <strong> "+nombre+" </strong> " + 
				"                          <span style=\"font-weight: 400;\">.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400; color: #000\">Confirmamos recepci&oacute;n de documentos, realizaremos la revisi&oacute;n para dar respuesta a la brevedad posible. " + 
				"							 <br>" + 
				"							 <br>" + 
				"						   El folio de seguimiento ser&aacute; <strong>" +numeroRegistro + "</strong> </span> " + 
				"                            <br>" + 
				"                          <span style=\"font-weight: 400;\"></span>" +
				"                            <br>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Si no realiz&oacute; ninguna aclaración, ignore este mensaje.</span> " + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                            <br>" + 
				"                          </span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">Mensaje autom&aacute;tico por favor no responda este mensaje</span>" + 
				"                        </p>" + 
				"                        <p>" + 
				"                          <span style=\"font-weight: 400;\">" + 
				"                            <br>" + 
				"                          </span> " + 
				"                          <span style=\"font-weight: 400;\">Gracias.</span>" + 
				"                        </p>" + 
				"                        <br>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr style=\"height: 40px;\">" + 
				"                      <td style=\"height: 40px; width: 1026px;\" valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!--END LAYOUT-13 ( 2-COL TEXT / BG ) --> <!--START LAYOUT-15 ( CONTACT US / ABOUT US )  -->" + 
				"    <tr>" + 
				"      <td class=\"container\" style=\"background-color: #2c2c31;\" align=\"center\" valign=\"top\"><!-- start container -->" + 
				"        <table class=\"container\" style=\"min-width: 600px; margin: 0 auto; background-color: #2c2c31; padding-left: 20px; padding-right: 20px;\" border=\"0\" width=\"600\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"          <tbody>" + 
				"            <tr>" + 
				"              <td valign=\"top\">" + 
				"                <table class=\"full-width\" style=\"margin: 0px auto; border-color: #0f0e0e; width: 560px;\" border=\"0\" width=\"560\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"><!-- start space -->" + 
				"                  <tbody>" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space --> <!-- start content container-->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\">" + 
				"                        <table border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">" + 
				"                          <tbody>" + 
				"                            <tr>" + 
				"                              <td align=\"center\" valign=\"top\">" + 
				"                                <table class=\"col-2\" dir=\"ltr\" style=\"height: 122px;\" border=\"0\" width=\"397\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 20px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; word-break: break-word; width: 397px;\" align=\"left\">" + 
				"                                        <span style=\"font-weight: 400;\">Aseguradora Patrimonial Vida SA de CV&nbsp;</span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"<!-- end space -->" + 
				"                                    <tr>" + 
				"                                      <td style=\"font-size: 14px; line-height: 24px; color: #ffffff; font-weight: normal; text-align: left; font-family: Roboto, Arial, Helvetica, sans-serif; padding-right: 10px; word-break: break-word; width: 387px;\" align=\"left\">" + 
				"                                        <span style=\"text-decoration: none; color: #ffffff; font-size: inherit; line-height: 24px;\">" + 
				"                                          <span style=\"font-weight: 400;\">Visite nuestra P&aacute;gina: www.spsegurospatrimonial.mx</span>." + 
				"                                        </span>" + 
				"                                      </td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                                <table class=\"space-w-25\" dir=\"ltr\" style=\"min-width: 25px; height: 1px; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-spacing: 0;\" border=\"0\" width=\"25\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">" + 
				"                                  <tbody>" + 
				"                                    <tr>" + 
				"                                      <td class=\"h-40\" style=\"display: block; font-size: 0px; line-height: 0; border-collapse: collapse;\" width=\"25\" height=\"1\">&nbsp;</td>" + 
				"                                    </tr>" + 
				"                                  </tbody>" + 
				"                                </table>" + 
				"<!-- [if (gte mso 9)|(IE)]></td><td valign=\"top\"><![endif]-->" + 
				"                              </td>" + 
				"                            </tr>" + 
				"                          </tbody>" + 
				"                        </table>" + 
				"                      </td>" + 
				"                    </tr>" + 
				"<!-- end content container--> <!-- start space -->" + 
				"                    <tr>" + 
				"                      <td valign=\"top\" height=\"40\">&nbsp;</td>" + 
				"                    </tr>" + 
				"<!-- end space -->" + 
				"                  </tbody>" + 
				"                </table>" + 
				"              </td>" + 
				"            </tr>" + 
				"          </tbody>" + 
				"        </table>" + 
				"<!-- end container -->" + 
				"      </td>" + 
				"    </tr>" + 
				"<!-- END LAYOUT-14 ( CONTACT US / ABOUT US ) -->" + 
				"  </tbody>" + 
				"</table>" + 
				"";
		return html;
	}
}
