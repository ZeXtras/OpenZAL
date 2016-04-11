package com.zimbra.cs.store.file;

import org.apache.commons.io.IOUtils;
import org.openzal.zal.Blob;
import org.openzal.zal.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InternalOverrideVolumeBlob extends VolumeBlob
{
  private final Blob mBlob;
  private final short mVolumeId;
  static File mFile = new File("/tmp/12345");

  public InternalOverrideVolumeBlob(Blob blob, short volumeId)
  {
    super(mFile, volumeId);
    mBlob = blob;
    mVolumeId = volumeId;
  }

  public Blob getWrappedObject()
  {
    return mBlob;
  }

  public short getVolumeId()
  {
    return mVolumeId;
  }

  @Override
  public File getFile()
  {
    // TODO tmp file?
    //return mBlob.getFile();
    return null;
  }

  @Override
  public String getPath()
  {
    return mBlob.getPath();
  }

  @Override
  public InputStream getInputStream() throws IOException
  {
    return new FileInputStream(mBlob.getFile());
  }

  @Override
  public boolean isCompressed() throws IOException
  {
    InputStream inputStream = new FileInputStream(mBlob.getFile());
    try
    {
      return Utils.isGzipped(inputStream);
    }
    finally
    {
      IOUtils.closeQuietly(inputStream);
    }
  }

  @Override
  public String getDigest() throws IOException
  {
    InputStream inputStream = new FileInputStream(mBlob.getFile());
    try
    {
      return Utils.computeDigest(inputStream);
    }
    finally
    {
      IOUtils.closeQuietly(inputStream);
    }
  }

  @Override
  public long getRawSize() throws IOException
  {
    //return mBlob.getRawSize();
    return 0;
  }

  @Override
  public com.zimbra.cs.store.Blob setCompressed(boolean isCompressed)
  {
    //mBlob.setCompressed(isCompressed);
    return this;
  }

  @Override
  public com.zimbra.cs.store.Blob setDigest(String digest)
  {
    //mBlob.setDigest(digest);
    return this;
  }

  @Override
  public com.zimbra.cs.store.Blob setRawSize(long rawSize)
  {
    //mBlob.setRawSize(rawSize);
    return this;
  }

  @Override
  public com.zimbra.cs.store.Blob copyCachedDataFrom(com.zimbra.cs.store.Blob other)
  {
    //mBlob.copyCachedDataFrom(new BlobWrap(other));
    return this;
  }

  /* $if ZimbraVersion >= 7.1.3 $ */
  public void renameTo(String newPath) throws IOException
  {
    mBlob.renameTo(newPath);
  }
  /* $elseif ZimbraVersion >= 7.0.0 $
  public boolean renameTo(String newPath)
  {
    renameTo(newPath);
    return true;
  }
  /* $elseif ZimbraVersion >= 6.0.15 $
  public void renameTo(String newPath) throws IOException
  {
    mBlob.renameTo(newPath);
  }
  /* $slse $
  public boolean renameTo(String newPath)
  {
    renameTo(newPath);
    return true;
  }
  /* $endif $ */

  @Override
  public String toString()
  {
    return mBlob.toString();
  }
}
