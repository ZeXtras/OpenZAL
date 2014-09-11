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

package org.openzal.zal.calendar;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public enum FreeBusyStatus
{
  Busy("B"),
  Free("F"),
  BusyTentative("T"),
  BusyUnavailable("O"),
  NoData("N");

  @NotNull
  private static final Map<String, FreeBusyStatus> sZimbra2Zal;

  static
  {
    sZimbra2Zal = new HashMap<String, FreeBusyStatus>(5);
    sZimbra2Zal.put("B", Busy);
    sZimbra2Zal.put("F", Free);
    sZimbra2Zal.put("T", BusyTentative);
    sZimbra2Zal.put("O", BusyUnavailable);
    sZimbra2Zal.put("N", NoData);
  }

  public String getRawFreeBusyStatus()
  {
    return mRawFreeBusyStatus;
  }

  private final String mRawFreeBusyStatus;

  FreeBusyStatus(String rawFreeBusyStatus)
  {
    mRawFreeBusyStatus = rawFreeBusyStatus;
  }

  @NotNull
  public static FreeBusyStatus fromZimbra(String freeBusy)
  {
    FreeBusyStatus status = sZimbra2Zal.get(freeBusy);

    if (status == null) {
      throw new RuntimeException("Invalid FreeBusyStatus: " + freeBusy);
    }

    return status;
  }
}
