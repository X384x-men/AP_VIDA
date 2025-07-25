package com.sytecso.component.utility;

import com.sytecso.dto.EmailBody;

public interface EmailPort {
	public boolean sendEmail(EmailBody emailBody);
}