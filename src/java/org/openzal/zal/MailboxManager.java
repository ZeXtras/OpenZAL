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
import org.openzal.zal.exceptions.ExceptionWrapper;
import org.openzal.zal.exceptions.ZimbraException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"StaticVariableOfConcreteClass", "StaticNonFinalField", "Singleton"})
public class MailboxManager
{
  private final          com.zimbra.cs.mailbox.MailboxManager                           mMailboxManager;
  @NotNull private final HashMap<MailboxManagerListener, MailboxManagerListenerWrapper> mListenerMap;

  public MailboxManager()
  {
    try
    {
      mMailboxManager = com.zimbra.cs.mailbox.MailboxManager.getInstance();
    }
    catch (com.zimbra.common.service.ServiceException ex)
    {
      throw ExceptionWrapper.wrap(ex);
    }

    mListenerMap = new HashMap<MailboxManagerListener, MailboxManagerListenerWrapper>();
  }

  public MailboxManager(Object mailboxManager)
  {
    mMailboxManager = (com.zimbra.cs.mailbox.MailboxManager) mailboxManager;
    mListenerMap = new HashMap<MailboxManagerListener, MailboxManagerListenerWrapper>();
  }

  public int[] getMailboxIds()
  {
    try
    {
  /* $if MajorZimbraVersion >= 7 $ */
      int ids[] = com.zimbra.cs.mailbox.MailboxManager.getInstance().getMailboxIds();
  /* $else$
      long longIds[] = com.zimbra.cs.mailbox.MailboxManager.getInstance().getMailboxIds();
      int ids[] = new int[ longIds.length ];

      for( int idx=0; idx < longIds.length; ++idx ) {
        ids[idx] = (int)longIds[idx];
      }
  /* $endif$ */

      return ids;
    }
    catch (com.zimbra.common.service.ServiceException ex)
    {
      throw new RuntimeException();
    }
  }

  public Set<Integer> getMailboxIdsSet()
  {
    int[] ids = getMailboxIds();
    Set<Integer> set = new HashSet<Integer>(ids.length);

    for( int n=0; n < ids.length; ++n ) {
      set.add(ids[n]);
    }

    return set;
  }


  public Set<Integer> getMailboxGroupSet()
  {
    int[] ids = getMailboxIds();
    Set<Integer> set = new HashSet<Integer>(100);

    for( int n=0; n < ids.length; ++n )
    {
      set.add( (ids[n]-1) % 100 + 1 );
    }

    return set;
  }

  public Mailbox getMailboxById(long mailboxId) throws ZimbraException
  {
    try
    {
      return new Mailbox(mMailboxManager.getMailboxById((int)mailboxId));
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public Mailbox getMailboxByAccount(Account account) throws ZimbraException
  {
    try
    {
      return new Mailbox(mMailboxManager.getMailboxByAccount(account.toZimbra(com.zimbra.cs.account.Account.class)));
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public Mailbox getMailboxByAccountId(String accountId) throws org.openzal.zal.exceptions.ZimbraException
  {
    try
    {
      return new Mailbox(mMailboxManager.getMailboxByAccountId(accountId));
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public int getMailboxCount()
  {
    return mMailboxManager.getMailboxCount();
  }

  public void addListener(MailboxManagerListener listener)
  {
    mMailboxManager.addListener(new MailboxManagerListenerWrapper(listener));
  }

  public void removeListener(MailboxManagerListener listener)
  {
    mMailboxManager.removeListener(new MailboxManagerListenerWrapper(listener));
  }
}
