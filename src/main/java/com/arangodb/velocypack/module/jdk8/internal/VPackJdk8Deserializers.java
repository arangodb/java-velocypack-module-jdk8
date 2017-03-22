/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.velocypack.module.jdk8.internal;

import java.lang.reflect.ParameterizedType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import com.arangodb.velocypack.VPackDeserializationContext;
import com.arangodb.velocypack.VPackDeserializer;
import com.arangodb.velocypack.VPackDeserializerParameterizedType;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;
import com.arangodb.velocypack.internal.VPackDeserializers;

/**
 * @author Mark - mark at arangodb.com
 *
 */
public class VPackJdk8Deserializers {

	public static final VPackDeserializer<Instant> INSTANT = (parent, vpack, context) -> {
		return VPackDeserializers.DATE.deserialize(parent, vpack, context).toInstant();
	};
	public static final VPackDeserializer<LocalDate> LOCAL_DATE = (parent, vpack, context) -> {
		return INSTANT.deserialize(parent, vpack, context).atZone(ZoneId.systemDefault()).toLocalDate();
	};
	public static final VPackDeserializer<LocalDateTime> LOCAL_DATE_TIME = (parent, vpack, context) -> {
		return INSTANT.deserialize(parent, vpack, context).atZone(ZoneId.systemDefault()).toLocalDateTime();
	};
	public static final VPackDeserializerParameterizedType<Optional<?>> OPTIONAL = new VPackDeserializerParameterizedType<Optional<?>>() {
		@Override
		public Optional<?> deserialize(
			final VPackSlice parent,
			final VPackSlice vpack,
			final VPackDeserializationContext context) throws VPackException {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Optional<?> deserialize(
			final VPackSlice parent,
			final VPackSlice vpack,
			final VPackDeserializationContext context,
			final ParameterizedType type) throws VPackException {
			return Optional.ofNullable(context.deserialize(vpack, Class.class.cast(type.getActualTypeArguments()[0])));
		}
	};
	public static final VPackDeserializer<OptionalDouble> OPTIONAL_DOUBLE = (parent, vpack, context) -> {
		final Double value = context.deserialize(vpack, Double.class);
		return (value != null) ? OptionalDouble.of(value) : OptionalDouble.empty();
	};
	public static final VPackDeserializer<OptionalInt> OPTIONAL_INT = (parent, vpack, context) -> {
		final Integer value = context.deserialize(vpack, Integer.class);
		return (value != null) ? OptionalInt.of(value) : OptionalInt.empty();
	};
	public static final VPackDeserializer<OptionalLong> OPTIONAL_LONG = (parent, vpack, context) -> {
		final Long value = context.deserialize(vpack, Long.class);
		return (value != null) ? OptionalLong.of(value) : OptionalLong.empty();
	};

}
