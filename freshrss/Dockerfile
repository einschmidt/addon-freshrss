ARG BUILD_FROM
# hadolint ignore=DL3006
FROM $BUILD_FROM

# Set shell
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Setup base
# hadolint ignore=DL3003,DL3018
RUN \
    set -eux \
    \
    && apk add --no-cache --virtual .build-dependencies \
        git=2.43.0-r0 \
    \
    && apk add --no-cache \
        tzdata=2023d-r0 \
        nginx=1.24.0-r14 \
        php81 php81-curl php81-gmp php81-intl php81-mbstring php81-xml php81-zip \
        php81-ctype php81-dom php81-fileinfo php81-iconv php81-json php81-opcache \
        php81-openssl php81-phar php81-session php81-simplexml php81-xmlreader \
        php81-xmlwriter php81-xml php81-tokenizer php81-zlib php81-fpm \
        php81-pdo_sqlite php81-pdo_mysql php81-pdo_pgsql \
    \
    && git clone --branch "1.22.1" --depth=1 \
        "https://github.com/FreshRSS/FreshRSS.git" /usr/share/freshrss \
    \
    # Disable built-in updates when using Docker, as the full image is supposed
    # to be updated instead.
    && sed -r -i "\\#disable_update#s#^.*#\t'disable_update' => true,#" \
        /usr/share/freshrss/config.default.php \
    \
    && rm -f -r \
        /etc/nginx \
        /etc/php8/php-fpm.d/www.conf \
    \
    && apk del --no-cache --purge \
        .build-dependencies

# Copy root filesystem
COPY rootfs /

# Build arguments
ARG BUILD_ARCH
ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION

# Labels
LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION} \
    maintainer="Einschmidt" \
    org.opencontainers.image.title="${BUILD_NAME}" \
    org.opencontainers.image.description="${BUILD_DESCRIPTION}" \
    org.opencontainers.image.vendor="Einschmidt" \
    org.opencontainers.image.authors="einschmidt" \
    org.opencontainers.image.licenses="MIT" \
    org.opencontainers.image.url="https://google.com" \
    org.opencontainers.image.source="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.documentation="https://github.com/${BUILD_REPOSITORY}/blob/main/README.md" \
    org.opencontainers.image.created=${BUILD_DATE} \
    org.opencontainers.image.revision=${BUILD_REF} \
    org.opencontainers.image.version=${BUILD_VERSION}
