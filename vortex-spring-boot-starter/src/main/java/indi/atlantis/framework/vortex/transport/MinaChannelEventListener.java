/**
* Copyright 2018-2021 Fred Feng (paganini.fy@gmail.com)

* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package indi.atlantis.framework.vortex.transport;

import org.apache.mina.core.session.IoSession;

import indi.atlantis.framework.vortex.common.ChannelEvent;
import indi.atlantis.framework.vortex.common.ChannelEventListener;
import indi.atlantis.framework.vortex.common.ChannelEvent.EventType;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * MinaChannelEventListener
 *
 * @author Fred Feng
 * @version 1.0
 */
@Slf4j
public class MinaChannelEventListener implements ChannelEventListener<IoSession> {

	@Override
	public void fireChannelEvent(ChannelEvent<IoSession> channelEvent) {
		if (log.isTraceEnabled()) {
			IoSession session = channelEvent.getSource();
			EventType eventType = channelEvent.getEventType();
			switch (eventType) {
			case CONNECTED:
				log.trace(session.getRemoteAddress() + " has established connection.");
				break;
			case CLOSED:
				log.trace(session.getRemoteAddress() + " has loss connection.");
				break;
			case PING:
				log.trace(session.getRemoteAddress() + " send a ping.");
				break;
			case PONG:
				log.trace(session.getRemoteAddress() + " send a pong.");
				break;
			case FAULTY:
				log.trace(session.getRemoteAddress() + " has loss connection for fatal reason.", channelEvent.getCause());
				break;
			}
		}
	}

}
