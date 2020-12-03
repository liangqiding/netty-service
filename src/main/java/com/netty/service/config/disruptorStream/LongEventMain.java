package com.netty.service.config.disruptorStream;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * description : TODO
 *
 * @author : qiDing
 * date: 2020-12-01 15:56
 * @version v1.0.0
 */
public class LongEventMain {
    public static void main(String[] args) throws Exception {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<ObjectEvent<Object>> disruptor = new Disruptor<>(ObjectEvent<Object>::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(
                (event, sequence, endOfBatch) -> System.out.println("Event1: " + event.getObj() + "===" + sequence),(event, sequence, endOfBatch) -> System.out.println("Event2: " + event.getObj() + "===" + sequence)
        );

        // Start the Disruptor, starts all threads running
        disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<ObjectEvent<Object>> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
//            event.set(buffer.getLong(0))
            String s = String.valueOf(l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setObj(s));
            Thread.sleep(1000);
        }
    }

}
