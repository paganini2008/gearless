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
package indi.atlantis.framework.vortex.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.paganini2008.devtools.ArrayUtils;

/**
 * 
 * HashPartitioner
 * 
 * @author Fred Feng
 * @version 1.0
 */
public class HashPartitioner implements Partitioner {

	private final Set<String> fieldNames = Collections.synchronizedSet(new HashSet<String>());

	public HashPartitioner(String... fieldNames) {
		addFieldNames(fieldNames);
	}

	public void addFieldNames(String... fieldNames) {
		if (ArrayUtils.isNotEmpty(fieldNames)) {
			this.fieldNames.addAll(Arrays.asList(fieldNames));
		}
	}

	public <T> T selectChannel(Object obj, List<T> channels) {
		Tuple tuple = (Tuple) obj;
		Object[] data = new Object[fieldNames.size()];
		int i = 0;
		for (String fieldName : fieldNames) {
			data[i++] = getFieldValue(tuple, fieldName);
		}
		try {
			return channels.get(indexFor(data, channels.size()));
		} catch (RuntimeException e) {
			return null;
		}
	}

	protected Object getFieldValue(Tuple tuple, String fieldName) {
		return tuple.getField(fieldName);
	}

	private static int indexFor(Object[] data, int length) {
		int hash = Arrays.deepHashCode(data);
		return (hash & 0x7FFFFFFF) % length;
	}

}
