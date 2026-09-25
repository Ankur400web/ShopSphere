interface NavLinkProps {
    label: string;
    href: string;
}

function NavLink({ label, href }: NavLinkProps) {
    return (
        <a  href={href}
            className="text-sm font-medium text-text-secondary transition-colors hover:text-primary-600"
        >
            {label}
        </a>
    );
}

export default NavLink;