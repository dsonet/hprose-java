/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * CharFieldAccessor.java                                 *
 *                                                        *
 * CharFieldAccessor class for Java.                      *
 *                                                        *
 * LastModified: Apr 27, 2015                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/
package hprose.io.accessor;

import hprose.common.HproseException;
import hprose.io.serialize.HproseWriter;
import hprose.io.serialize.ValueWriter;
import hprose.io.unserialize.HproseReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public final class CharFieldAccessor implements MemberAccessor {
    private final long offset;

    public CharFieldAccessor(Field accessor) {
        accessor.setAccessible(true);
        offset = Accessors.unsafe.objectFieldOffset(accessor);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void serialize(HproseWriter writer, Object obj) throws IOException {
        char value;
        try {
            value = Accessors.unsafe.getChar(obj, offset);
        }
        catch (Exception e) {
            throw new HproseException(e.getMessage());
        }
        ValueWriter.write(writer.stream, value);
    }

    @Override
    public void unserialize(HproseReader reader, ByteBuffer buffer, Object obj) throws IOException {
        char value = reader.readChar(buffer);
        try {
            Accessors.unsafe.putChar(obj, offset, value);
        }
        catch (Exception e) {
            throw new HproseException(e.getMessage());
        }
    }

    @Override
    public void unserialize(HproseReader reader, InputStream stream, Object obj) throws IOException {
        char value = reader.readChar(stream);
        try {
            Accessors.unsafe.putChar(obj, offset, value);
        }
        catch (Exception e) {
            throw new HproseException(e.getMessage());
        }
    }
}