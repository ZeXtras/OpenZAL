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

import com.zimbra.cs.mailbox.ACL;
import com.zimbra.cs.mailbox.MailItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class Folder extends Item
{
  public Folder(@NotNull Object item)
  {
    super((MailItem)item);
  }

  public Folder(@NotNull Item item)
  {
    super(item);
  }

  @Nullable
  public Acl getACL()
  {
    ACL acl = ((com.zimbra.cs.mailbox.Folder) mMailItem).getACL();
    if (acl == null)
    {
      return null;
    }
    return new Acl(acl);
  }

  @NotNull
  public String getUrl()
  {
    return ((com.zimbra.cs.mailbox.Folder) mMailItem).getUrl();
  }

  public byte getDefaultView()
  {
    return Item.byteType(((com.zimbra.cs.mailbox.Folder) mMailItem).getDefaultView());
  }

  public byte getAttributes()
  {
    return ((com.zimbra.cs.mailbox.Folder) mMailItem).getAttributes();
  }
}
