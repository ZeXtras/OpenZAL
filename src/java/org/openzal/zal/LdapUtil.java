/*
 * ZAL - The abstraction layer for Zimbra.
 * Copyright (C) 2014 ZeXtras S.r.l.
 *
 * This file is part of ZAL.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, version 2 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ZAL. If not, see <http://www.gnu.org/licenses/>.
 */

package org.openzal.zal;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
/* $if ZimbraVersion < 8.0.0 $
import org.openzal.zal.exceptions.ExceptionWrapper;
import com.zimbra.cs.account.ldap.ZimbraLdapContext;
import com.zimbra.cs.account.ldap.LdapEntry;
/* $endif $ */

public class LdapUtil
{
  public static void modifyAttrs(@NotNull Account account, Map<String, Object> attrs )
  {
    /* $if ZimbraVersion < 8.0.0 $
    try
    {
      ZimbraLdapContext zlc = new ZimbraLdapContext(true);
      com.zimbra.cs.account.Account zimbraAccount = account.toZimbra(com.zimbra.cs.account.Account.class);
      com.zimbra.cs.account.ldap.LdapUtil.modifyAttrs(zlc, ((LdapEntry) zimbraAccount).getDN(), attrs, zimbraAccount);
    }
    catch (Exception t)
    {
      throw ExceptionWrapper.wrap(t);
    }
    /* $else $ */
    throw new UnsupportedOperationException();
    /* $endif $ */
  }
}
