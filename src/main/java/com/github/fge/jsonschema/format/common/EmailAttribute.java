/*
 * Copyright (c) 2014, Francis Galiegue (fgaliegue@gmail.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of this file and of both licenses is available at the root of this
 * project or, if you have the jar distribution, in directory META-INF/, under
 * the names LGPL-3.0.txt and ASL-2.0.txt respectively.
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.fge.jsonschema.format.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;

/**
 * Validator for the {@code email} format attribute.
 *
 * <p><b>Important note</b>: the basis for email format validation is <a
 * href="http://tools.ietf.org/html/rfc5322">RFC 5322</a>. The RFC mandates that
 * email addresses have a domain part. However, that domain part may consist of
 * a single domain name component. As such, {@code foo@bar} is considered valid.
 * </p>
 */
public final class EmailAttribute
    extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE = new EmailAttribute();

    public static FormatAttribute getInstance()
    {
        return INSTANCE;
    }

    private EmailAttribute()
    {
        super("email", NodeType.STRING);
    }

    @Override
    public void validate(final ProcessingReport report,
        final MessageBundle bundle, final FullData data)
        throws ProcessingException
    {
        final String value = data.getInstance().getNode().textValue();

        if (!checkEmail(value)) {
        	 report.error(newMsg(data, bundle, "err.format.invalidEmail")
                     .putArgument("value", value));
		}
//        try {
//            new InternetAddress(value, true);
//        } catch (AddressException ignored) {
//           
//        }
    }
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    private boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
}
