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

import org.openzal.zal.exceptions.ExceptionWrapper;
import com.zimbra.common.service.ServiceException;
import com.zimbra.cs.account.AttributeClass;
import org.jetbrains.annotations.NotNull;


public class DataSourceType
{
  public static String gal  = "gal";
  public static String rss  = "rss";
  public static String imap = "imap";
  public static String pop3 = "pop3";

  public static String gal_OCName  = AttributeClass.galDataSource.getOCName();
  public static String rss_OCName  = AttributeClass.rssDataSource.getOCName();
  public static String imap_OCName = AttributeClass.imapDataSource.getOCName();
  public static String pop3_OCName = AttributeClass.pop3DataSource.getOCName();

  @NotNull
  private final String mDataSourceType;

  protected DataSourceType(@NotNull Object type)
  {
    if (type == null)
    {
      throw new NullPointerException();
    }

    /* $if ZimbraVersion >= 8.0.0 $ */
    mDataSourceType = ((com.zimbra.soap.admin.type.DataSourceType) type).name();
    /* $else $
    mDataSourceType = ((com.zimbra.cs.account.DataSource.Type)type).name();
    /* $endif $ */
  }

  public static DataSourceType fromString(String dataSourceType)
  {
    try
    {
      /* $if ZimbraVersion >= 8.0.0 $ */
      return new DataSourceType(com.zimbra.soap.admin.type.DataSourceType.fromString(dataSourceType));
      /* $else $
      return new DataSourceType(com.zimbra.cs.account.DataSource.Type.fromString(dataSourceType));
      /* $endif $ */
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  @NotNull
  public String name()
  {
    return mDataSourceType;
  }

  @Override
  public int hashCode()
  {
    return mDataSourceType.hashCode();
  }

  @Override
  public boolean equals(Object other)
  {
    return mDataSourceType.equals(other);
  }

  protected <T> T toZimbra(Class<T> cls)
  {
    try
    {
      /* $if ZimbraVersion >= 8.0.0 $ */
      return cls.cast(com.zimbra.soap.admin.type.DataSourceType.fromString(mDataSourceType));
      /* $else $
      return cls.cast(com.zimbra.cs.account.DataSource.Type.fromString(mDataSourceType));
      /* $endif $ */
    }
    catch (ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }
}
