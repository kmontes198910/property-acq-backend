#!/bin/bash
# Este script limpiará el token de GitHub del historial de Git

# Primero, creamos una copia de seguridad de la rama actual
git branch -c dev dev-backup

# Reemplazamos el token real por la variable de entorno en todos los archivos rc-settings.xml
git filter-branch --force --tree-filter '
    find . -name "rc-settings.xml" -type f -exec sed -i.bak "s/<password>[^<]*<\/password>/<password>\${env.PACKAGE_TOKEN}<\/password>/g" {} \;
' --prune-empty -- --all

echo "==========================================="
echo "El historial ha sido limpiado."
echo "Revisa los cambios con: git log -p"
echo "Si todo parece correcto, haz un push forzado con: git push -f origin dev"
echo "Si algo salió mal, puedes restaurar con: git reset --hard dev-backup"
echo "==========================================="
