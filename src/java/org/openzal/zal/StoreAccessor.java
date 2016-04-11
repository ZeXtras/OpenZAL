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

import org.jetbrains.annotations.Nullable;
import org.openzal.zal.exceptions.ZimbraException;

import java.io.IOException;
import java.io.InputStream;

public interface StoreAccessor
{
  //boolean hasVolume(short volumeId);
  @Nullable MailboxBlob getMailboxBlob(Mailbox mbox, int msgId, int revision, String locator)
    throws ZimbraException;
  MailboxBlob copy(Blob src, Mailbox destMbox, int destMsgId, int destRevision)
    throws IOException, ZimbraException;
  MailboxBlob copy(Blob src, Mailbox destMbox, int destMsgId, int destRevision, short volumeId)
    throws IOException, ZimbraException;
  MailboxBlob link(Blob src, Mailbox destMbox, int destMsgId, int destRevision)
    throws IOException, ZimbraException;
  MailboxBlob link(Blob src, Mailbox destMbox, int destMsgId, int destRevision, short volumeId)
    throws IOException, ZimbraException;
  String getBlobPath(int mboxId, int itemId, int revision, short volumeId) throws ZimbraException;
  boolean delete(Mailbox mailbox) throws IOException;
  boolean delete(Blob blob) throws IOException;
  void startup() throws IOException, ZimbraException;
  void shutdown();
  boolean supports(StoreFeature feature);
  BlobBuilder getBlobBuilder(short volumeId) throws IOException, ZimbraException;
  @Nullable InputStream getContent(Blob blob) throws IOException;
  MailboxBlob renameTo(StagedBlob src, Mailbox destMbox, int destMsgId, int destRevision) throws IOException;
}

