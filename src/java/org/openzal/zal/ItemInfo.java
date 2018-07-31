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

package org.openzal.zal;

import org.jetbrains.annotations.NotNull;

public class ItemInfo extends ItemStatus
{
  public final String itemId;

  public ItemInfo(int itemId, int sequence, long date)
  {
    this(String.valueOf(itemId), sequence,date);
  }

  public ItemInfo(int itemId, @NotNull ItemStatus itemStatus )
  {
    this(itemId, itemStatus.getSequence(), itemStatus.getDate());
  }

  public ItemInfo(String itemId, int sequence, long date)
  {
    super(sequence, date);
    this.itemId = itemId;
  }

  @Override
  public int hashCode()
  {
    return getRawItemId().hashCode() + getSequence();
  }

  @Override
  public boolean equals(Object object)
  {
    if(!(object instanceof ItemInfo))
    {
      return false;
    }

    ItemInfo other = (ItemInfo) object;

    return other.getSequence() == getSequence()
           && other.getDate() == getDate()
           && other.getRawItemId().equals(getRawItemId());
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();

    builder.append("itemId: ").append(getRawItemId()).append(" ");
    builder.append("sq: ").append(getSequence()).append(" ");
    builder.append("dt: ").append(getDate()).append(" ");

    return builder.toString();
  }

  public int getItemId()
    throws NumberFormatException
  {
    return Integer.valueOf(getRawItemId());
  }

  public String getRawItemId()
  {
    return itemId;
  }

}
