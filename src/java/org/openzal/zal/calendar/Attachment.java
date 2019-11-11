/*
 * ZAL - The abstraction layer for Zimbra.
 * Copyright (C) 2016 ZeXtras S.r.l.
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

package org.openzal.zal.calendar;

public class Attachment
{
  /* $if ZimbraVersion > 8.5.0 $ */
  private com.zimbra.common.calendar.Attach mZAttach;
  /* $endif $ */

  public Attachment(Object zAttach)
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      mZAttach = (com.zimbra.common.calendar.Attach) zAttach;
    }
    /* $endif $ */
  }

  public String getUri()
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      return mZAttach.getUri();
    }
    /* $else $
    {
      return null;
    }
    /* $endif $ */
  }

  public String getFileName()
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      return mZAttach.getFileName();
    }
    /* $else $
    {
      return null;
    }
    /* $endif $ */
  }

  public void setFileName(String fileName)
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      mZAttach.setFileName(fileName);
    }
    /* $endif $ */
  }

  public String getContentType()
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      return mZAttach.getContentType();
    }
    /* $else $
    {
      return null;
    }
    /* $endif $ */
  }

  public void setContentType(String contentType)
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      mZAttach.setContentType(contentType);
    }
    /* $endif $ */

  }

  public String getBinary64Data()
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      return mZAttach.getBinaryB64Data();
    }
    /* $else $
    {
      return null;
    }
    /* $endif $ */
  }

  public byte[] getDecodedData()
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      return mZAttach.getDecodedData();
    }
    /* $else $
    {
      return null;
    }
    /* $endif $ */
  }

  public <T> T toZimbra(Class<T> targetClass)
  {
    /* $if ZimbraVersion > 8.5.0 $ */
    {
      return targetClass.cast(mZAttach);
    }
    /* $else $
    {
      return null;
    }
    /* $endif $ */
  }
}