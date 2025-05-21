server {
    listen {{ .interface }}:{{ .ingress_port }} default_server;

    include /etc/nginx/includes/server_params.conf;

    allow 172.30.32.2;
    deny all;

    # Handle PHP files under ingress path
    location ~ ^{{ .ingress_path }}/(.+\.php)(/.*)?$ {
        rewrite ^{{ .ingress_path }}/(.+\.php)(/.*)?$ /$1$2 break;

        fastcgi_pass 127.0.0.1:9002;
        fastcgi_index index.php;
        fastcgi_read_timeout 900;

        fastcgi_split_path_info ^(/.+\.php)(/.*)?$;
        set $path_info $fastcgi_path_info;

        fastcgi_param SCRIPT_FILENAME $document_root$fastcgi_script_name;
        fastcgi_param PATH_INFO $path_info;
        fastcgi_param SCRIPT_NAME $fastcgi_script_name;

        include /etc/nginx/includes/fastcgi_params.conf;
    }

    # Static + fallback handler
    location ~ ^{{ .ingress_path }}(/.*)?$ {
        rewrite ^{{ .ingress_path }}(/.*)?$ $1 break;
        try_files $1 $1/ /index.php$is_args$args;
    }
}
