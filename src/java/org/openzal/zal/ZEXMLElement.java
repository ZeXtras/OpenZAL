/*
 * ZAL - An abstraction layer for Zimbra.
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

import com.zimbra.common.soap.Element;
import org.jetbrains.annotations.NotNull;

public class ZEXMLElement
{
  private final Element.XMLElement mXMLElement;

  public ZEXMLElement(String name)
  {
    mXMLElement = new Element.XMLElement(name);
  }

  protected ZEXMLElement(@NotNull Object element)
  {
    if ( element == null )
    {
      throw new NullPointerException();
    }
    mXMLElement = (Element.XMLElement)element;
  }

  public ZEXMLElement clone()
  {
    return new ZEXMLElement(mXMLElement.clone());
  }

  public void addAttribute(String key, Boolean value)
  {
    mXMLElement.addAttribute(key, value);
  }

  public void addAttribute(String key, Integer value)
  {
    mXMLElement.addAttribute(key, value);
  }

  public void addAttribute(String key, String value)
  {
    mXMLElement.addAttribute(key, value);
  }

  public <T> T toZimbra(Class<T> cls)
  {
    return cls.cast(mXMLElement);
  }
}
