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

import java.io.File;
import java.util.*;

import org.jetbrains.annotations.Nullable;
import org.openzal.zal.exceptions.*;
import org.openzal.zal.exceptions.ZimbraException;

/* $if ZimbraVersion >= 8.0.0 $ */
import com.zimbra.cs.volume.*;
/* $else$
import com.zimbra.cs.store.file.*;
import com.zimbra.cs.db.*;
/* $endif$ */
import com.google.inject.Singleton;

@Singleton
public class VolumeManager
{
  private static final short sMboxGroupBits = 8;
  private static final short sMboxBits = 12;
  private static final short sFileGroupBits = 8;
  private static final short sFileBits = 12;

  /* $if ZimbraVersion >= 8.0.0 $ */
  private final com.zimbra.cs.volume.VolumeManager  mVolumeManager;

  public VolumeManager()
  {
    mVolumeManager = com.zimbra.cs.volume.VolumeManager.getInstance();
  }
  /* $endif $ */

  public List<StoreVolume> getAll()
  {
    /* $if ZimbraVersion >= 8.0.0 $ */
    List<Volume> list = mVolumeManager.getAllVolumes();
    /* $else$
    List<Volume> list = Volume.getAll();
    /* $endif$ */

    return ZimbraListWrapper.wrapVolumes(list);
  }

  public StoreVolume update(String id, short type,
                            String name, String path,
                            boolean compressBlobs, long compressionThreshold)
    throws ZimbraException
  {

    StoreVolume volumeToUpdate = getById(id);

    Volume vol;
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      Volume.Builder builder = Volume.builder();
      builder.setId(Short.parseShort(id));
      builder.setName(name);
      builder.setType(type);
      if (!path.startsWith(File.separator))
      {
        builder.setPath(path, false);
      }
      else
      {
        builder.setPath(path, true);
      }
      builder.setMboxGroupBits(volumeToUpdate.getMboxGroupBits());
      builder.setMboxBit(volumeToUpdate.getMboxBits());
      builder.setFileGroupBits(volumeToUpdate.getFileGroupBits());
      builder.setFileBits(volumeToUpdate.getFileBits());
      builder.setCompressBlobs(compressBlobs);
      builder.setCompressionThreshold(compressionThreshold);
      vol = builder.build();
      vol = mVolumeManager.update(vol);
    }
    catch (com.zimbra.cs.volume.VolumeServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $else $
    //TODO check synchronize.. check sVolumeMap..
    if (path.startsWith(File.separator))
    {
      try
      {
        vol = Volume.update(Short.parseShort(id), type, name, path,
                                   volumeToUpdate.getMboxGroupBits(),
                                   volumeToUpdate.getMboxBits(),
                                   volumeToUpdate.getFileGroupBits(),
                                   volumeToUpdate.getFileBits(),
                                   compressBlobs, compressionThreshold, false);
        Volume.reloadVolumes();
      }
      catch (com.zimbra.cs.store.file.VolumeServiceException e)
      {
        throw ExceptionWrapper.wrap(e);
      }
      catch (com.zimbra.common.service.ServiceException e)
      {
        throw ExceptionWrapper.wrap(e);
      }
    }
    else
    {
      com.zimbra.cs.db.DbPool.Connection conn = null;
      try
      {
        conn = com.zimbra.cs.db.DbPool.getConnection();
        vol = DbVolume.update(conn, Short.parseShort(id), type, name, path,
                                 volumeToUpdate.getMboxGroupBits(),
                                 volumeToUpdate.getMboxBits(),
                                 volumeToUpdate.getFileGroupBits(),
                                 volumeToUpdate.getFileBits(),
                                 compressBlobs, compressionThreshold
        );
        conn.commit();
      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
      finally
      {
        com.zimbra.cs.db.DbPool.quietClose(conn);
      }
    }
    /* $endif$ */
    return new StoreVolume(vol);
  }

  public StoreVolume create(short id, short type,
                            String name, String path,
                            boolean compressBlobs, long compressionThreshold)
    throws ZimbraException
  {
    Volume vol;
    /* $if ZimbraVersion >= 8.0.0 $ */
    try
    {
      Volume.Builder builder = Volume.builder();
      builder.setId(id);
      builder.setType(type);
      builder.setName(name);
      if (!path.startsWith(File.separator))
      {
        builder.setPath(path, false);
      }
      else
      {
        builder.setPath(path, true);
      }
      builder.setMboxGroupBits(sMboxGroupBits);
      builder.setMboxBit(sMboxBits);
      builder.setFileGroupBits(sFileGroupBits);
      builder.setFileBits(sFileBits);
      builder.setCompressBlobs(compressBlobs);
      builder.setCompressionThreshold(compressionThreshold);

      vol = builder.build();
      vol = mVolumeManager.create(vol);
    }
    catch (com.zimbra.cs.volume.VolumeServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    /* $else$
    //TODO check synchronize.. check sVolumeMap..
    if (path.startsWith(File.separator))
    {
      try
      {
        vol = Volume.create(id, type, name, path,
                            sMboxGroupBits, sMboxBits, sFileGroupBits, sFileBits,
                            compressBlobs, compressionThreshold, false);
      }
      catch (com.zimbra.cs.store.file.VolumeServiceException e)
      {
        throw ExceptionWrapper.wrap(e);
      }
      catch (com.zimbra.common.service.ServiceException e)
      {
        throw ExceptionWrapper.wrap(e);
      }
    }
    else
    {
      com.zimbra.cs.db.DbPool.Connection conn = null;
      try
      {
        conn = com.zimbra.cs.db.DbPool.getConnection();
        vol = DbVolume.create(conn, id, type, name, path,
                              sMboxGroupBits, sMboxBits, sFileGroupBits, sFileBits,
                              compressBlobs, compressionThreshold
        );
        conn.commit();
        Volume.reloadVolumes();
      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
      finally
      {
        com.zimbra.cs.db.DbPool.quietClose(conn);
      }
    }
    /* $endif$ */
    return new StoreVolume(vol);
  }

  public void setCurrentVolume(short volType, short id)
    throws ZimbraException
  {
    try
    {
      /* $if MajorZimbraVersion <= 7 $
      Volume.setCurrentVolume(volType, id);
      /* $else$ */
      mVolumeManager.setCurrentVolume(volType, id);
      /* $endif$ */
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public void setCurrentSecondaryVolume(String id)
    throws ZimbraException
  {
    Short volType = Short.valueOf("2");
    try
    {
      /* $if MajorZimbraVersion <= 7 $
      Volume.setCurrentVolume(volType, Short.valueOf(id));
      /* $else$ */
      mVolumeManager.setCurrentVolume(volType, Short.valueOf(id));
      /* $endif$ */
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  /*
  public StoreVolume setDefaultBits(String id)
    throws ZimbraException
  {

    StoreVolume volumeToUpdate = getById(id);

    Volume vol;
    try
    {
      /* if MajorZimbraVersion <= 7 $
      vol = Volume.update(volumeToUpdate.getId(),
                                 volumeToUpdate.getType(),
                                 volumeToUpdate.getName(),
                                 volumeToUpdate.getRootPath(),
                                 sMboxGroupBits,
                                 sMboxBits,
                                 sFileGroupBits,
                                 sFileBits,
                                 volumeToUpdate.getCompressBlobs(),
                                 volumeToUpdate.getCompressionThreshold(),
                                 false);
      /* else$
      Volume.Builder builder = Volume.builder();
      builder.setId(Short.parseShort(volumeToUpdate.getId()));
      builder.setName(volumeToUpdate.getName());
      builder.setType(volumeToUpdate.getType());
      builder.setPath(volumeToUpdate.getRootPath(), true);
      builder.setMboxGroupBits(sMboxGroupBits);
      builder.setMboxBit(sMboxBits);
      builder.setFileGroupBits(sFileGroupBits);
      builder.setFileBits(sFileBits);
      builder.setCompressBlobs(volumeToUpdate.getCompressBlobs());
      builder.setCompressionThreshold(volumeToUpdate.getCompressionThreshold());
      vol = builder.build();
      vol = mVolumeManager.update(vol);
      /* $endif$
    }
    /* if MajorZimbraVersion <= 7 $
    catch (com.zimbra.cs.store.file.VolumeServiceException e)
    /* else$
    catch (com.zimbra.cs.volume.VolumeServiceException e)
    /* endif$
    {
      throw ExceptionWrapper.wrap(e);
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
    return new StoreVolume(vol);
  }
  */

  public boolean delete(String id) throws ZimbraException
  {
    try
    {
      /* $if MajorZimbraVersion <= 7 $
      return Volume.delete(Short.valueOf(id));
      /* $else$ */
      return mVolumeManager.delete(Short.valueOf(id));
      /* $endif$ */
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  public StoreVolume getById(String vid) throws org.openzal.zal.exceptions.ZimbraException
  {
    try
    {
      /* $if MajorZimbraVersion <= 7 $
      return new StoreVolume(Volume.getById(vid));
      /* $else$ */
      return new StoreVolume(mVolumeManager.getVolume(vid));
      /* $endif$ */
    }
    catch (com.zimbra.common.service.ServiceException e)
    {
      throw ExceptionWrapper.wrap(e);
    }
  }

  @Nullable
  public StoreVolume getCurrentSecondaryMessageVolume()
  {
    Volume vol;
    /* $if MajorZimbraVersion <= 7 $
    vol = Volume.getCurrentSecondaryMessageVolume();
    /* $else$ */
    vol = mVolumeManager.getCurrentSecondaryMessageVolume();
    /* $endif$ */

    if( vol != null )
    {
      return new StoreVolume(vol);
    }
    else
    {
      return null;
    }
  }

  public boolean hasSecondaryMessageVolume()
  {
    return getCurrentSecondaryMessageVolume() != null;
  }

  public List<StoreVolume> getByType(short type)
  {
    /* $if MajorZimbraVersion <= 7 $
    List<Volume> list = Volume.getByType(type);
    /* $else$ */
    List<Volume> list = mVolumeManager.getAllVolumes();
    /*  $endif$ */

    ArrayList<StoreVolume> newList = new ArrayList<StoreVolume>(list.size());
    for( Volume vol : list )
    {
      if( vol.getType() == type ) {
        newList.add(new StoreVolume(vol));
      }
    }
    return newList;
  }

  public StoreVolume getCurrentMessageVolume()
  {
    /* $if MajorZimbraVersion <= 7 $
    return new StoreVolume(Volume.getCurrentMessageVolume());
    /* $else$ */
    return new StoreVolume(mVolumeManager.getCurrentMessageVolume());
    /* $endif$ */
  }

  public StoreVolume getCurrentIndex()
  {
    /* $if MajorZimbraVersion <= 7 $
    return new StoreVolume(Volume.getCurrentIndexVolume());
    /* $else$ */
    return new StoreVolume(mVolumeManager.getCurrentIndexVolume());
    /* $endif$ */
  }

  public StoreVolume getVolumeByName(String volumeName)
  {
    for (StoreVolume storeVolume : getAll())
    {
      if (storeVolume.getName().equals(volumeName))
      {
        return storeVolume;
      }
    }

    return null;
  }

  public boolean isValidVolume(String id)
  {
    // check type message?
    List<StoreVolume> volumeList = getAll();

    for (StoreVolume v:volumeList){
      if (v.getId().equals(id))
      {
        return true;
      }
    }

    return false;
  }
}
